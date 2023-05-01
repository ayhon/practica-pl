package ditto;

import ditto.ast.Module;
import ditto.ast.ProgramOutput;
import ditto.lexer.Lexer;
import ditto.parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;

public class Test {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java Test <task> <filepath>");
            return;
        }

        String task = args[0];
        String filePath = args[1];
        Boolean printAST = false;
        printAST = args.length > 2 && args[2].equals("ast");

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        var parentFolder = file.getParentFile();

        try (Reader input = new InputStreamReader(new FileInputStream(file));) {
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer);
            parser.parse();
            Module main = parser.getRoot();
            main.setClassFolder(parentFolder.getAbsolutePath());
            main.setName(filePath);

            switch (task) {
                case "ast" -> {
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
                    main.computeOffset();
                }
                case "code" -> {
                    var output = new ProgramOutput();
                    main.compile(output);

                    File watFile = new File(parentFolder,
                            String.format("compiled/%s", file.getName().replace(".ditto", ".wat")));
                    watFile.getParentFile().mkdirs();

                    try (FileWriter fileWriter = new FileWriter(watFile)) {
                        fileWriter.write(output.toString());
                    } catch (Exception e) {
                        System.err.println(String.format(
                                "[ERROR]: Se ha producido un error al intentar escribir el código en el archivo %s",
                                watFile.getName()));
                    }
                }
            }
            if (printAST) {
                System.out.println(main);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}