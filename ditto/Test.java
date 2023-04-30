package ditto;

import ditto.ast.Module;
import ditto.ast.ProgramOutput;
import ditto.errors.BindingError;
import ditto.lexer.Lexer;
import ditto.parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Test {
    public static final String[] tasks = { "ast", "bind", "typecheck", "typesize", "offsets", "code" };
    public static boolean useAsMain = true;

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

        var parentFolder = file.getParentFile();
        Reader input = new InputStreamReader(new FileInputStream(file));
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        parser.parse();
        Module main = parser.getRoot();
        main.setClassFolder(parentFolder.getAbsolutePath());
        main.setName(filePath);

        try {
            switch (task) {
                case "ast" -> {
                    if(useAsMain) System.out.println(main);
                }
                case "bind" -> {
                    main.bind();
                }
                case "typecheck" -> {
                    main.typecheck();
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
        } catch (Exception e) {
            if(useAsMain){
                System.out.println(e);
                e.printStackTrace();
            }
            throw e;
        }
    }
}
