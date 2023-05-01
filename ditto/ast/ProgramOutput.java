package ditto.ast;

import ditto.ast.definitions.DefFunc;
import ditto.ast.types.VoidType;

public class ProgramOutput {
    /// Clase que va encadenando las instrucciones de salida
    private final StringBuilder sb = new StringBuilder();
    private final int memory_size;
    private int indent_level = 0;

    public final static String LOCAL_START = "localsStart";
    private final String FUNC_SIG = "_sig_func";
    private final int INDENT_WIDTH = 2;

    public ProgramOutput(int memory_size) {
        this.memory_size = memory_size;
    }

    public ProgramOutput() {
        this(2000);
    }

    public void append(String instruction) {
        sb.append(instruction.indent(indent_level));
        sb.append('\n');
    }

    public void append(String instruction, Object... args) {
        append(String.format(instruction, args));
    }

    @Override
    public String toString() {
        StringBuilder f = new StringBuilder();
        f.append("(module\n");
        f.append(String.format("(memory %d)", memory_size));
        f.append("(start $main)");
        f.append("(type $_sig_i32 (func (param i32)))");
        f.append("(type $_sig_ri32 (func (result i32)))");
        f.append(String.format("(type $%s (func))", FUNC_SIG)); // TODO: Mirar si esto tiene sentido en WASM
        f.append("(global $SP (mut i32) (i32.const 0))         ;; start of stack");
        f.append("(global $MP (mut i32) (i32.const 0))         ;; mark pointer");
        f.append("(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4");
        // Este es el tipo de todas nuestras funciones, pues nos
        // pasamos los argumentos y valores de retorno por memoria
        f.append("(import \"runtime\" \"print\" (func $print (type $_sig_i32)))");
        f.append("(import \"runtime\" \"scan\" (func $scan (type $_sig_ri32)))");
        f.append(sb.toString());
        f.append("(export \"memory\" (memory 0))"); // For debugging purposes
        f.append(")");

        return sb.toString();
    }

    public void comment(String comment) {
        append(";; %s", comment);
    }

    public void reserveStack() {
        /**
         * Funcion que reserva espacio en la pila
         * Pasa por parametro de WASM el tamaño que se quiere reservar
         * Deberia devolver la posicion del antiguo MP (dynamic link)
         * 
         * MP: Mark Pointer, indica la posicion inicial de la zona de memoria reservada
         * en Stack
         * SP: Stack Pointer, indica la cota superior de la zona de memoria reservada en
         * Stack
         * NP: Puntero que apunta a la cota inferior de zona de heap (porque crece hacia
         * abajo)
         */
        append("""
                (func $reserveStack (param $size i32) (result i32)
                    get_global $MP          ;; Para devolver al final

                        get_global $SP
                    set_global $MP          ;; Ahora MP vale SP (Cota superior de la zona de memoria reservada en Stack anterior = Inicio de la zona de memoria reservada en Stack actual)

                            get_global $SP
                            get_local $size
                        i32.add
                    set_global $SP          ;; Nuevo SP = SP anterior + size que reservamos ahora

                        get_global $SP
                        get_global $NP      ;; Obtener el valor de NP
                    i32.gt_u                ;; Si resulta que SP > NP, entonces hay overflow

                    if
                        i32.const 3
                        call $exception     ;; Lanzar excepcion con codigo 3
                    end
                )
                """);
    }

    public void postReserveStack() {
        /**
         * MEM[MP] = MP antiguo
         * MEM[MP+4]= SP actual
         */
        append("""
                ;; Guarda en $temp el valor de $MP antiguo
                set_local $temp

                ;; Guarda en MEM[$MP] el valor de $MP antiguo
                get_global $MP       ;;; Este es MP nuevo = SP antiguo
                get_local $temp
                i32.store            ;;; Guarda en MEM[MP] el valor de MP antiguo

                    get_global $MP
                    get_global $SP       ;;; Este es SP nuevo (con el espacio reservado)
                i32.store offset=4       ;;; Guarda en MEM[MP+4] el valor de SP nuevo

                ;; Calcular el inicio del stack para las variables locales (8 + MP porque usamos 2 casillas para puntero dinamico)
                        get_global $MP
                        i32.const 8
                    i32.add
                """);
        set_local(LOCAL_START);
    }

    public void freeStack() {
        /**
         * Funcion que libera espacio en la pila
         * Hay que tener en la cima el valor del MP antiguo (el que queremos restaurar)
         * MP' SP'
         * MP SP | |
         * ↓ ↓ ↓ ↓
         * |SP| | | | | | | | | |SP|MP| | | | | | |
         */

        append("""
                (func $freeStack (type $_sig_void) ;;
                                get_global $MP
                            i32.load            ;; a la casila MEM[MP] (contiene MP antiguo)
                        i32.load offset=4       ;; a la casilla MEM[MP_ANTIGUO + 4] (contiene SP antiguo)
                    set_global $SP              ;; SP = SP antiguo

                            get_global $MP
                        i32.load
                    set_global $MP
                )
                """); /**
                       * MEM[MEM[$MP] + 4] = SP antiguo
                       * MEM[$MP] = MP antiguo
                       */
    }

    public void reserveHeapSpace() {
        /*
         * Función que reserva memoria en el heap. Recibe por parámetro el tamaño que se
         * quiere reservar. Devuelve la dirección de memoria donde se ha reservado el
         * espacio.
         */
        append("""
                (func $reserveHeapSpace (param $size i32) (result i32)
                    get_local $size
                    get_global $NP
                    i32.sub
                    set_global $NP  ;; NP = NP - size
                    get_global $SP
                    get_global $NP  ;; Comprobamos que no haya overflow
                    i32.gt_u
                    if
                    i32.const 3
                    call $exception
                    end
                )
                """);
    }

    private void dedent() {
        indent_level = Math.max(0, indent_level - INDENT_WIDTH);
    }

    private void indent() {
        indent_level += INDENT_WIDTH;
    }

    /* ==== WASM OPERATIONS ==== */
    public void drop() {
        append("drop");
    }

    /* i32 MEMORY OPERATIONS */
    public void i32_load() {
        append("i32.load");
    }

    public void i32_load(String x) {
        append("(i32.load (%s))", x);
    }

    public void i32_load(int offset) {
        append("i32.load offset=%d", offset);
    }

    public void i32_load(int offset, String x) {
        append("(i32.load offset=%d (%s))", offset, x);
    }

    public void i32_store() {
        append("i32.store");
    }

    /* i32 OPERATIONS */
    public void i32_const(int i) {
        append("i32.const %d", i);
    }

    public void i32_add() {
        append("i32.add");
    }

    public void i32_sub() {
        append("i32.sub");
    }


    public void i32_mul() {
        append("i32.mul");
    }

    public void i32_div_s() {
        append("i32.i32_div_s");
    }

    public void i32_rem_s() {
        append("i32.rem_s");
    }


    public void i32_eq() {
        append("i32.eq");
    }

    public void i32_ne() {
        append("i32.ne");
    }

    public void i32_eq_z() {
        append("i32.eq_z");
    }

    public void i32_le_s() {
        append("i32.le_s");
    }

    public void i32_lt_s() {
        append("i32.lt_s");
    }

    public void i32_ge_s() {
        append("i32.ge_s");
    }

    public void i32_gt_s() {
        append("i32.gt_s");
    }

    public void i32_and() {
        append("i32.and");
    }

    public void i32_or() {
        append("i32.or");
    }

    public void i32_xor() {
        append("i32.xor");
    }

    /* LOCALS */
    public void local(String name, String type) {
        append("(local %s %s)", name, type);
    }

    public void get_local(String name) {
        append("get_local $%s", name);
    }

    public void get_local(int index) {
        append("get_local %d", index);
    }

    public void set_local(String name) {
        append("set_local $%s", name);
    }

    public void set_local(int index) {
        append("set_local %d", index);
    }

    public void tee_local(String name) {
        append("tee_local $%s", name);
    }

    public void tee_local(int index) {
        append("tee_local %d", index);
    }

    /* GLOBALS */
    public void global(String name, String type) {
        append("(global %s %s)", name, type);
    }

    public void global(String name, String type, String value) {
        append("(global %s %s (%s.const %s))", name, type, type, value);
    }

    public void global_mut(String name, String type) {
        append("(global %s (mut %s))", name, type);
    }

    public void global_mut(String name, String type, String value) {
        append("(global %s (mut %s) (%s.const %s))", name, type, type, value);
    }

    public void get_global(String name) {
        append("get_global $%s", name);
    }

    public void get_global(int index) {
        append("get_global %d", index);
    }

    public void set_global(String name) {
        append("set_global $%s", name);
    }

    public void set_global(int index) {
        append("set_global %d", index);
    }

    /* FUNCTIONS */
    public void call(String name) {
        append("call $%s", name);
    }

    public void func(DefFunc fun, Runnable runnable) {
        append("(func $%s", fun.getIden());
        indent();
        append(fun.getResult().asWasmResult());
        runnable.run(); // La idea es que haga algo con el ProgramOutput dentro del runnable
        dedent();
        append(")");
    }

    /* CONTROL FLOW */
    public void block(Runnable runnable) {
        append("block");
        indent();
        runnable.run();
        dedent();
        append("end");
    }

    public void loop(Runnable runnable) {
        append("loop");
        indent();
        runnable.run();
        dedent();
        append("end");
    }

    public void block_loop(Runnable runnable) {
        block(() -> loop(runnable));
    }

    public void br(int skip) {
        append("br %d", skip);
    }

    public void br_if(int skip) {
        append("br_if %d", skip);
    }

    public void br_if(int skip, String condition) {
        append("(br_if %d (%s))", skip, condition);
    }
    // public void br_table(int[] skip){
    //
    // }

    public void if_else(Runnable then, Runnable els) {
        append("if");
        indent();
        then.run();
        dedent();
        append("else");
        indent();
        append("end");
    }

    public void if_(Runnable then){
        append("if");
        indent();
        then.run();
        dedent();
        append("end");
    }

    public void br_table(int size) {
        append("br_table");
        indent();
        for (int i = 0; i < size; i++) {
            append("%d", i);
        }
        dedent();
    }

    //Solo usar estos dos metodos en el match
    public void block() {
        append("block");
        indent();
    }

    public void end() {
        dedent();
        append("end");
    }
}