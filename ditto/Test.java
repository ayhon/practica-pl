package ditto;

import ditto.ast.Module;
import ditto.ast.ProgramOutput;
import ditto.errors.SemanticError;
import ditto.errors.TypeError;
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
        for (int i = 1; i < args.length; i++) {
            String filePath = args[i];
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
                }
                case "bind" -> {
                    try {
                        main.bind();
                    } catch (SemanticError e) {
                        System.out.println(e);
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
                case "typecheck" -> {
                    try {
                        main.typecheck();
                    } catch (TypeError e) {
                        System.err.println(e);
                        e.printStackTrace();
                        System.err.println(main);
                        System.exit(1);
                    }
                }
                case "typesize" -> {
                    main.computeTypeSize();
                }
                case "offsets" -> {
                    main.computeTypeSize();
                }
                case "code" -> {
                    main.compile(new ProgramOutput());
                }
            }
            System.err.println(main);
        }
    }
}
