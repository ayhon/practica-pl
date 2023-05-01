package ditto.ast.definitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Context;
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

    @Override
    public String getAstString() {
        return "import";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(name);
    }

    public String getIden() {
        return name;
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(this.getModule());
    }

    private void loadModule(String path) {
        File file = new File(path, name + ".ditto");
        if (!file.exists()) {
            throw new ModuleImportError("Module " + name + " not found under " + path);
        }

        /// Obtener el módulo asociado leyendo el archivo
        try (Reader input = new InputStreamReader(new FileInputStream(file))) {
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer);
            parser.parse();
            this.module = parser.getRoot();
            this.module.setName(path + "/" + name);
            this.module.setClassFolder(path);
        } catch (IOException e) {
            throw new ModuleImportError("Error IO parsing module " + name);
        } catch (Exception e) {
            throw new ModuleImportError("Error parsing module " + name);
        }
    }

    public Module getModule() {
        return module;
    }

    @Override
    public void bind(Context ctx) {
        if (module == null){
            loadModule(ctx.getModule().getClassFolder());
        }
        module.bind();
    }

    @Override
    public Type type() {
        return VoidType.getInstance();
    }

    @Override
    public void compile(ProgramOutput out) {
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

}
