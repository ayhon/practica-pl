package eval;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexicoEval;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 AnalizadorLexicoEval alex = new AnalizadorLexicoEval(input);
	 Evaluador evaluador = new Evaluador(alex);
	 System.out.println(((Integer)evaluador.parse().value).intValue());
 }
}   
   
