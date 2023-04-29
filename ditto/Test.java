package ditto;

import ditto.ast.ProgramOutput;
import ditto.lexer.Lexer;
import ditto.parser.Parser;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Test {
    public static void main(String[] args) throws Exception {
        String task = args[0];
        String file = args[1];

        Reader input = new InputStreamReader(new FileInputStream(file));
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);

        switch (task) {
            case "ast" -> {
                parser.parse();
                System.out.println(parser.getRoot());
                break;
            }
            case "bind" -> {
                parser.parse();
                parser.getRoot().bind();
                break;
            }
            case "typecheck" -> {
                parser.parse();
                parser.getRoot().typecheck();
                break;
            }
            case "code" -> {
                parser.parse();
                parser.getRoot().compile(new ProgramOutput());
                break;
            }
        }
    }
}
