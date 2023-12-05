package ES_23_24_1sem.LETI.Grupo_N;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Class representing a schedule.
 */
public class Schedule extends HashData {

	private LinkedHashMap<String,String> mapping = new LinkedHashMap<>();

	public Schedule	(LinkedHashMap<String,String> mapping) {
		super();
		this.mapping = mapping;
	}

	/**
	 * Creates a Schedule object by reading data from a local file.
	 * 
	 * @param path The path to the local file.
	 * @return The created Schedule object.
	 */
	public static Schedule createScheduleByLocalFile(String path) {

		Schedule schedule = new Schedule();

		try {

			Scanner sc = new Scanner(new File(path));
			schedule.readFile(sc);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return schedule;
	}

	/**
	 * Creates a Schedule object by reading data from a remote file.
	 * 
	 * @param urlStr The URL of the remote file.
	 * @return The created Schedule object.
	 */
	public static Schedule createScheduleByRemoteFile(String urlStr) {

		Schedule schedule = new Schedule();

		try {

			InputStream in = new URL(urlStr).openStream();
			schedule.readFile(new Scanner(in));
			in.close();

		} catch (MalformedURLException e) {
			System.err.println("Invalid URL: " + urlStr);
			e.printStackTrace();

		} catch (IOException e) {
			System.err.println("Failed to open the remote file: " + urlStr);
			e.printStackTrace();
		}

		return schedule;
	}
	@Override
	public void readFile(Scanner sc) {
		super.readFile(sc);
	
		

		while (sc.hasNextLine())
			super.addInfoByLine(sc.nextLine());
	}
}