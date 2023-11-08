package ES_23_24_1sem.LETI.Grupo_N;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class App {
	
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to read local or remote archives? (l/r): ");

		switch (scanner.nextLine()) {

		case "l":
			System.out.println("Type the path for local archive: ");
			Schedule schedule = Schedule.createScheduleByLocalFile(scanner.nextLine());
			System.out.println(schedule.map.get("Unidade Curricular"));
			break;

		case "r":
			System.out.println("Type the URL for remote archive: ");
			readRemoteArchive(scanner.nextLine());
			break;

		default:
			System.out.println("Invalid Option");
			break;
		}
		scanner.close();
	}

	/*private File getLocalArchive(String path) {
		return new File(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public static void readRemoteArchive(String urlStr) {
		try {
			InputStream in = new URL(urlStr).openStream();
			Scanner scanner = new Scanner(in);
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			scanner.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
		
		
		
		
}