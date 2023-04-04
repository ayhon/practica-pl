package ditto.parser;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import ditto.lexer.Lexer;

public class Test {
    public static void main(String[] args) throws Exception {
        Reader input = new InputStreamReader(new FileInputStream(args[0]));
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        parser.parse();
    }
}
