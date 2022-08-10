import org.boluj.Interpreter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainClass {
     public static void main(String[] args) {
         Interpreter e = new Interpreter();
        while(true){
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print(">");
                e.parse(br.readLine());
                e.exec();
            }catch (Exception err) {}
            e.clearParsedCode();
        }
    }
}
