package ES_23_24_1sem.LETI.Grupo_N;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
/**
 * Class representing a schedule.
 */
public class Schedule {

	private LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
	/**
     * Default constructor for the Schedule class.
     */
	public Schedule() {
	}
	
	/**
     * Creates a Schedule object by reading data from a local file.
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

	/**
     * Adds information to the schedule by reading a line of data.
     * @param line The line of data.
     */
	private void addInfoByLine(String line) {

		String[] data = my_split(line);
		int i = 0;
		for (String key : map.keySet())
			map.get(key).add(data[i++]);
	}

	  /**
     * Splits a line of data into an array of strings.
     * @param line The line of data.
     * @return The array of strings.
     */
	private static String[] my_split(String line) {
		String[] arr = new String[11];
		int i,s,f;
		i = s = f = 0;
		for (int j = 0; j < line.length(); j++) {
			if (line.charAt(j) == ';') {
				arr[i++] = (s == f) ? "N/A" : line.substring(s, f);
				s = f + 1;
			}
			f++;
		}
		arr[i] = (s == f) ? "N/A" : line.substring(s, f);
		return arr;
	}

	/**
     * Reads data from a file to populate the schedule.
     * @param sc The Scanner object used to read the file.
     */
	public void readFile(Scanner sc) {
		String line = sc.nextLine();
		String[] labels = line.split(";");

		for (int i = 0; i < labels.length; i++)
			map.put(labels[i], new ArrayList<String>());

		while (sc.hasNextLine())
			addInfoByLine(sc.nextLine());
	}

	/**
     * Gets the map representing the schedule.
     * @return The map representing the schedule.
     */
	public LinkedHashMap<String, List<String>> getMap() {
		return map;
	}

	
}