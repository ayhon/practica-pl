package ditto.ast;

import java.util.StringJoiner;

import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.DefVar;
import ditto.ast.definitions.DefFunc.Param;
import ditto.errors.SemanticError;

public class ProgramOutput {
    /// Clase que va encadenando las instrucciones de salida
    private final StringBuilder sb = new StringBuilder();
    private final int memory_size;
    private int indent_level = 0;
    private boolean has_start = false;

    public final static String LOCAL_START = "localsStart";
    public final static String RESERVE_STACK = "reserveStack";
    public final static String FREE_STACK = "freeStack";
    public final static String RESERVE_HEAP = "reserveHeap";

    private final String FUNC_SIG = "_sig_void";
    private final int INDENT_WIDTH = 4;

    public ProgramOutput(int memory_size) {
        this.memory_size = memory_size;
    }

    public ProgramOutput() {
        this(2000);
    }

    private void append(String instruction) {
        sb.append(instruction.indent(indent_level));
    }

    private void append(String instruction, Object... args) {
        append(String.format(instruction, args));
    }

    public String toStringNoBoilerplate() {
        return """
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                ;; ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BUILTINS ESTAS COMPILANDO SIN BULTINS
                """ + sb.toString();
    }

    @Override
    public String toString() {
        StringJoiner buf = new StringJoiner("\n");
        buf.add("(module");
        buf.add("""
                (import "runtime" "print" (func $print (param i32)))
                (import "runtime" "scan" (func $scan (result i32)))""");
        buf.add("""
                (import "runtime" "exceptionHandler" (func $exception (type $_sig_i32)))""");
        buf.add(String.format("(memory %d)", memory_size));
        buf.add("(start $start)");
        buf.add("(type $_sig_i32i32i32 (func (param i32 i32 i32) ))");
        buf.add("(type $_sig_i32 (func (param i32)))");
        buf.add("(type $_sig_ri32 (func (result i32)))");
        buf.add(String.format("(type $%s (func))", FUNC_SIG)); // TODO: Mirar si esto tiene sentido en WASM
        buf.add("(global $SP (mut i32) (i32.const 0))         ;; start of stack");
        buf.add("(global $MP (mut i32) (i32.const 0))         ;; mark pointer");
        buf.add("(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4");
        // Este es el tipo de todas nuestras funciones, pues nos
        // pasamos los argumentos y valores de retorno por memoria
        loadBuiltins(buf);
        buf.add(sb.toString());
        buf.add("""
                (export "memory" (memory 0))
                (export "init" (func $start))
                """); // For debugging purposes
        buf.add(")");

        return buf.toString();
    }

    public void loadBuiltins(StringJoiner sb) {
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
        sb.add("""
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
        /**
         * Funcion que libera espacio en la pila
         * Hay que tener en la cima el valor del MP antiguo (el que queremos restaurar)
         * MP' SP'
         * MP SP | |
         * ↓ ↓ ↓ ↓
         * |SP| | | | | | | | | |SP|MP| | | | | | |
         */
        sb.add("""
                (func $freeStack (type $_sig_void) ;;
                                get_global $MP
                            i32.load            ;; a la casila MEM[MP] (contiene MP antiguo)
                        i32.load offset=4       ;; a la casilla MEM[MP_ANTIGUO + 4] (contiene SP antiguo)
                    set_global $SP              ;; SP = SP antiguo

                            get_global $MP
                        i32.load
                    set_global $MP
                )
                """);
        /**
         * Función que reserva memoria en el heap. Recibe por parámetro el tamaño que se
         * quiere reservar. Devuelve la dirección de memoria donde se ha reservado el
         * espacio.
         */
        sb.add("""
                (func $reserveHeap (param $size i32)
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
        // ADDR 0 0 0 0 0 0 0 0 0 0 0 0
        //
        sb.add("""
                (func $copyn (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest
                    (param $src i32)
                    (param $dest i32)
                    (param $n i32)
                    block
                    loop
                        get_local $n
                        i32.eqz
                        br_if 1
                        get_local $n
                        i32.const 1
                        i32.sub
                        set_local $n
                        get_local $dest
                        get_local $src
                        i32.load
                        i32.store
                        get_local $dest
                        i32.const 4
                        i32.add
                        set_local $dest
                        get_local $src
                        i32.const 4
                        i32.add
                        set_local $src
                        br 0
                    end
                    end
                )
                """);
    }

    public void comment(String comment) {
        append(";; %s", comment);
    }

    public void reserveStack() {
        call(RESERVE_STACK);
        postReserveStack();
    }

    private void postReserveStack() {
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
        call("freeStack");
    }

    public void reserveHeapSpace() {
        call("reserveHeapSpace");
    }

    public void inStart(String mainFunction, Runnable runnable) {
        if (has_start)
            throw new SemanticError("Can't have more than one start of the program");
        append("(func $start (type $%s)", FUNC_SIG);
        indented(() -> {
            runnable.run();
            call(mainFunction);
        });
        append(")");
    }

    private void indent() {
        indent_level += INDENT_WIDTH;
    }

    private void dedent() {
        indent_level = Math.max(0, indent_level - INDENT_WIDTH);
    }

    private void indented(Runnable r){
        indent();
        r.run();
        dedent();
    }

    /* ==== WASM OPERATIONS ==== */

    /* STACK JUGGLING */
    public void drop() {
        append("drop");
    }

    public void duplicate() {
        tee_local("temp");
        get_local("temp");
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
        append("i32.div_s");
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

    public void i32_eqz() {
        append("i32.eqz");
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

    /* MEMORY LOCALS */
    public void mem_location(DefVar var) {
        if (var.isGlobal()) {
            i32_const(8 + var.getOffset());
        } else {
            mem_local(var.getOffset());
        }
    }

    public void mem_local(int offset) {
        get_local(ProgramOutput.LOCAL_START);
        i32_const(offset);
        i32_add();
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
        indented(() -> {
            append(fun.getResult().asWasmResult());
            append(String.format("(local $%s i32)", ProgramOutput.LOCAL_START));
            append("(local $temp i32)");
    
            int stackSize = fun.getSize() + 4 + 4;
            i32_const(stackSize);
            reserveStack();
    
            runnable.run(); // La idea es que haga algo con el ProgramOutput dentro del runnable
    
            freeStack();
        });
        append(")");
    }

    public void doReturn() {
        append("return");
        freeStack();
    }

    /* CONTROL FLOW */
    public void block(Runnable runnable) {
        append("block");
        indented(runnable);
        append("end");
    }

    public void blocks(int n, Runnable runnable) {
    }

    public void loop(Runnable runnable) {
        append("loop");
        indented(runnable);
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
        indented(then);
        append("else");
        indented(els);
        append("end");
    }

    public void if_(Runnable then) {
        append("if");
        indented(then);
        append("end");
    }

    public void br_table(int size) {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("br_table");
        for (int i = 0; i < size; i++) {
            sj.add(String.format("%d", i));
        }
        indented(() -> append(sj.toString()));
    }
    public interface IntRunnable {
        void run(int i);
    }
    public void br_table(int size, IntRunnable r) {
        for(int i = 0; i < size; i++){
            append("block");
            indent();
        }
        // Tabla de branching
        block(() -> br_table(size));
        for(int i = 0; i < size; i++){
            r.run(i);
            dedent();
            append("end");
        }
    }
}