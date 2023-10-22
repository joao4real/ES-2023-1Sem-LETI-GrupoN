package ES_23_24_1sem.LETI.Grupo_N;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class App {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to read local or remote archives? (l/r): ");
		String option = scanner.nextLine();

		switch (option) {

		case "l":
			System.out.println("Type the path for local archive: ");
			String path = scanner.nextLine();
			readLocalArchive(path);
			break;
			
		case "r":
			System.out.println("Type the URL for remote archive: ");
			String url = scanner.nextLine();
			readRemoteArchive(url);
			break;

		default:
			System.out.println("Invalid Option");
			break;
		}
		scanner.close();
	}

	public static void readLocalArchive(String path) {
		try {
			Scanner scanner = new Scanner(new File(path));
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			scanner.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readRemoteArchive(String urlStr) {
		try {
			URL url = new URL(urlStr);
			InputStream in = url.openStream();
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