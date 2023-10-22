package ES_23_24_1sem.LETI.Grupo_N;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
   
	public void read(File file) {
		
		
		String splitBy = ";";
		File f = new File("HorarioDeExemplo.CSV");
		
		try {
			
			Scanner sc = new Scanner(f);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		
	}
	
	
	
	
	
	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
