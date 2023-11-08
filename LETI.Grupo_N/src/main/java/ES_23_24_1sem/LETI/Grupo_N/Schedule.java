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

public class Schedule {

	private LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();

	public Schedule() {
	}

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

	private void addInfoByLine(String line) {

		String[] data = my_split(line);
		int i = 0;
		for (String key : map.keySet())
			map.get(key).add(data[i++]);
	}

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

	public void readFile(Scanner sc) {
		String line = sc.nextLine();
		String[] labels = line.split(";");

		for (int i = 0; i < labels.length; i++)
			map.put(labels[i], new ArrayList<String>());

		while (sc.hasNextLine())
			addInfoByLine(sc.nextLine());
	}

	public LinkedHashMap<String, List<String>> getMap() {
		return map;
	}

	
}