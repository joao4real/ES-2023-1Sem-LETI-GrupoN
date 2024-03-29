package ES_23_24_1sem.LETI.Grupo_N;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public abstract class HashData {

	private LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
	private String[] labels;

	public HashData() {
	}

	/**
	 * Adds information to the hash-data by reading a line of data.
	 * 
	 * @param line The line of data.
	 */
	public void addInfoByLine(String line) {

		String[] data = my_split(line);
		int i = 0;
		for (String key : map.keySet())
			map.get(key).add(data[i++]);
	}

	/**
	 * Splits a line of data into an array of strings.
	 * 
	 * @param line The line of data.
	 * @return The array of strings.
	 */

	String[] my_split(String line) {
		String[] arr = new String[getMapSize()];
		int i, s, f;
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
	 * Reads data from a file to populate the hash-data.
	 * 
	 * @param sc The Scanner object used to read the file.
	 */
	public void readFile(Scanner sc) {
		String line = sc.nextLine();
		this.setLabels(line.split(";"));

		for (int i = 0; i < getMapSize(); i++)
			map.put(labels[i], new ArrayList<String>());

		while (sc.hasNextLine())
			addInfoByLine(sc.nextLine());
	}

	/**
	 * Gets the map representing the hash-data.
	 * 
	 * @return The map representing the hash-datae.
	 */
	public LinkedHashMap<String, List<String>> getMap() {
		return map;
	}

	public void setMap(LinkedHashMap<String, List<String>> map) {
		this.map = map;
	}
	
	public int getMapSize() {
		return labels.length;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}
}
