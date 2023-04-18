package ditto.ast;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import ditto.lexer.Lexer;
import ditto.parser.Parser;

public class Test {
    public static void main(String[] args) throws Exception {
        Reader input = new InputStreamReader(new FileInputStream(args[1]));
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        switch(args[0]){
            case "ast":
                parser.parse();
                System.out.println(parser.getRoot());
            case "bind":
                parser.parse();
                try {
                    parser.getRoot().bind();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            case "type":
                parser.parse();
                try {
                    parser.getRoot().type();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            case "code":
                parser.parse();
                try {
                    parser.getRoot().generateCode(null);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
        }
    }
}

