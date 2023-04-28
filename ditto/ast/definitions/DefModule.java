package ditto.ast.definitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.Module;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;
import ditto.errors.ModuleImportError;
import ditto.lexer.Lexer;
import ditto.parser.Parser;

public class DefModule extends Node {
    private final String name;
    private Module module = null;

    public DefModule(String name) {
        this.name = name;
    }

    public DefModule(List<String> listName) {
        this.name = String.join("::", listName);
    }

    @Override
    public String getAstString() {
        return "import";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(name);
    }

    @Override
    public Type type() {
        return VoidType.getInstance();
    }

    @Override
    public void compile(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

    @Override
    public void bind(GlobalContext global, LocalContext local) {
        /// Add the module to the global scope
        global.addModule(this);
        // We dont' load the module
    }

    public String getIden() {
        return name;
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList();
    }

    private void loadModule() {
        /// Obtener el módulo asociado leyendo el archivo
        try (Reader input = new InputStreamReader(new FileInputStream(name + ".ditto"))) {
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer);
            parser.parse();
            this.module = parser.getRoot();
        } catch (IOException e) {
            throw new ModuleImportError("Error IO parsing module " + name);
        } catch (Exception e) {
            throw new ModuleImportError("Error parsing module " + name);
        }
    }

    public Module getModule() {
        if (module == null)
            loadModule();
        return module;
    }
}
