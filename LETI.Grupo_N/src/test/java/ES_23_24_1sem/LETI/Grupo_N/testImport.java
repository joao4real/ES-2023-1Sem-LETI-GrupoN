package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class testImport {
	
	   @Test
	    public void testReadLocalArchive() {
	        try {
	            String filePath = "C:\\Users\\Pedro\\OneDrive - ISCTE-IUL\\3A-1S\\HorarioDeExemplo.csv";
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            PrintStream ps = new PrintStream(baos);
	            PrintStream old = System.out;
	            System.setOut(ps);
	            App.readLocalArchive(filePath);
	            System.out.flush();
	            System.setOut(old);
	            Scanner scanner = new Scanner(new File(filePath));
	            StringBuilder sb = new StringBuilder();
	            while (scanner.hasNextLine()) {
	                sb.append(scanner.nextLine());
	                sb.append("\r\n");
	            }
	            scanner.close();
	            String fileContent = sb.toString();
	            assertEquals(fileContent, baos.toString());
	        } catch (Exception e) {
	            fail("Exception thrown during test: " + e.toString());
	        }
	    }

    @Test
    public void testReadRemoteArchive() {
        try {
            String urlStr = "https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/HorarioDeExemplo.csv";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);
            App.readRemoteArchive(urlStr);
            System.out.flush();
            System.setOut(old);
            URL url = new URL(urlStr);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            reader.close();
            String fileContent = sb.toString();
            assertEquals(fileContent, baos.toString());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.toString());
        }
    }
}