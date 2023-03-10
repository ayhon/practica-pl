package ditto.lexer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Test {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Reader input = new InputStreamReader(new FileInputStream(args[0]));
        Lexer al = new Lexer(input);
        Token unidad;
        do {
          unidad = al.yylex();
          System.out.println(unidad);
        }
        while (unidad.clase() != ClaseLexica.EOF);
       }   
}
