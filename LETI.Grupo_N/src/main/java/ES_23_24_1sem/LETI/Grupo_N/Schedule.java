package ES_23_24_1sem.LETI.Grupo_N;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Schedule {

	public LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();

	private Schedule() {
	}

	public static Schedule createScheduleByLocalFile(String path) {

		Schedule schedule = new Schedule();

		try {
			Scanner sc = new Scanner(new File(path));
			String line = sc.nextLine();
			String[] labels = line.split(";");

			for (int i = 0; i < labels.length; i++)
				schedule.map.put(labels[i], new ArrayList<String>());

			while (sc.hasNextLine())
				schedule.addInfoByLine(sc.nextLine());

			sc.close();
		} catch (FileNotFoundException e) {
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
        int i = 0;
        int s = 0;
        int f = 0;
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
}