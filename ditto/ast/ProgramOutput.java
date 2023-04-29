package ditto.ast;

import ditto.ast.definitions.DefFunc;
import ditto.ast.types.VoidType;

public class ProgramOutput {
    /// Clase que va encadenando las instrucciones de salida
    private final StringBuilder sb = new StringBuilder();
    private final int memory_size;
    private int indent_level = 0;

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
        // Este es el tipo de todas nuestras funciones, pues nos
        // pasamos los argumentos y valores de retorno por memoria
        f.append("(import \"runtime\" \"print\" (func $print (type $_sig_i32)))");
        f.append("(import \"runtime\" \"scan\" (func $scan (type $_sig_ri32)))");
        f.append(sb.toString());
        f.append("(export \"memory\" (memory 0))"); // For debugging purposes
        f.append(")");

        return sb.toString();
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

    public void i32_store(String expr) {
        append("i32.store $%s", expr);
    }

    public void i32_store() {
        append("i32.store");
    }

    /* i32 OPERATIONS */
    public void i32_const(int i) {
        append("i32.const %d", i);
    }

    public void i32_add(String x, String y) {
        append("(i32.add (%s) (%s))", x, y);
    }

    public void i32_add() {
        append("i32.add");
    }

    public void i32_sub(String x, String y) {
        append("(i32.sub (%s) (%s))", x, y);
    }

    public void i32_sub() {
        append("i32.sub");
    }

    public void i32_mul(String x, String y) {
        append("(i32.mul (%s) (%s))", x, y);
    }

    public void i32_mul() {
        append("i32.mul");
    }

    public void i32_div_s() {
        append("i32.i32_div_s");
    }

    public void i32_div_s(String x, String y) {
        append("(i32.i32_div_s (%s) (%s))", x, y);
    }

    public void i32_rem_s() {
        append("i32.rem_s");
    }

    public void i32_rem_s(String x, String y) {
        append("(i32.rem_s (%s) (%s))", x, y);
    }

    public void i32_eq() {
        append("i32.eq");
    }

    public void i32_eq(String x, String y) {
        append("(i32.eq (%s) (%s))", x, y);
    }

    public void i32_ne() {
        append("i32.ne");
    }

    public void i32_ne(String x, String y) {
        append("(i32.ne (%s) (%s))", x, y);
    }

    public void i32_eq_z() {
        append("i32.eq_z");
    }

    public void i32_eq_z(String x, String y) {
        append("(i32.eq_z (%s) (%s))", x, y);
    }

    public void i32_le_s() {
        append("i32.le_s");
    }

    public void i32_le_s(String x, String y) {
        append("(i32.le_s (%s) (%s))", x, y);
    }

    public void i32_lt_s() {
        append("i32.lt_s");
    }

    public void i32_lt_s(String x, String y) {
        append("(i32.lt_s (%s) (%s))", x, y);
    }

    public void i32_ge_s() {
        append("i32.ge_s");
    }

    public void i32_ge_s(String x, String y) {
        append("(i32.ge_s (%s) (%s))", x, y);
    }

    public void i32_gt_s() {
        append("i32.gt_s");
    }

    public void i32_gt_s(String x, String y) {
        append("(i32.gt_s (%s) (%s))", x, y);
    }

    public void i32_and() {
        append("i32.and");
    }

    public void i32_and(String x, String y) {
        append("(i32.and (%s) (%s))", x, y);
    }

    public void i32_or() {
        append("i32.or");
    }

    public void i32_or(String x, String y) {
        append("(i32.or (%s) (%s))", x, y);
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
        append("(func $%s (type $%s)", fun.getIden(), FUNC_SIG);
        indent();

        for (DefFunc.Param var : fun.getParams()) {
            append("(param $%s %s)", var.getIden(), var.getType());
        }
        if (fun.getResult() != VoidType.getInstance()) {
            append("(result %s)");
        }
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

    public void _if() {
        append("if");
        indent();
    }

    public void _else() {
        dedent();
        append("else");
        indent();
    }

    public void _end() {
        dedent();
        append("end");
    }
}