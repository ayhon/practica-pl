package ditto;

import ditto.ast.Module;
import ditto.ast.ProgramOutput;
import ditto.errors.SemanticError;
import ditto.lexer.Lexer;
import ditto.parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Test {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java Test <task> <filepath>");
            return;
        }

        String task = args[0];
        String filePath = args[1];

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        var classFolder = file.getParentFile().getAbsolutePath();
        Reader input = new InputStreamReader(new FileInputStream(file));
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        parser.parse();
        Module main = parser.getRoot();
        main.setClassFolder(classFolder);
        main.setName(filePath);

        switch (task) {
            case "ast" -> {
                System.out.println(main);
                break;
            }
            case "bind" -> {
                try{
                    main.bind();
                } catch (SemanticError e) {
                    System.out.println(e);
                    e.printStackTrace();
                    System.exit(1);
                }
                break;
            }
            case "typecheck" -> {
                main.typecheck();
                System.out.println(main);
                break;
            }
            case "code" -> {
                main.compile(new ProgramOutput());
                break;
            }
        }
    }
}
