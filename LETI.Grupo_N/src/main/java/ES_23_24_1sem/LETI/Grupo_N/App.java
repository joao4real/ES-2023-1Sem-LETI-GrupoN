package ES_23_24_1sem.LETI.Grupo_N;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
	
	public static void main(String[] args) {
		
		Schedule schedule = new Schedule();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to read local or remote archives? (l/r): ");

		switch (scanner.nextLine()) {

		case "l":
			System.out.println("Type the path for local archive: ");
			schedule = Schedule.createScheduleByLocalFile(scanner.nextLine());
			System.out.println(schedule.getMap().get("Unidade Curricular"));
			break;

		case "r":
			System.out.println("Type the URL for remote archive: ");
			schedule = Schedule.createScheduleByRemoteFile(scanner.nextLine());
			System.out.println(schedule.getMap().get("Unidade Curricular"));
			break;

		default:
			System.out.println("Invalid Option");
			break;
		}
		
		scanner.close();
		
		try {
		    FileWriter fw = new FileWriter("SalasDeAulaPorTipoDeSala.html");
		    fw.write("MCPILADEAÇO");
		    System.out.println("Passei por aqui");
		    fw.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
}