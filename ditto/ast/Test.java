package ditto.ast;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import ditto.lexer.Lexer;
import ditto.parser.Parser;

public class Test {
    public static void main(String[] args) throws Exception {
        Reader input = new InputStreamReader(new FileInputStream(args[0]));
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        parser.parse();
        System.out.println(parser.getRoot());
    }
}

