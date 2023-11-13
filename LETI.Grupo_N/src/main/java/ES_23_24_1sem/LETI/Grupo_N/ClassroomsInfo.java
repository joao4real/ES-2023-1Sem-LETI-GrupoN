package ES_23_24_1sem.LETI.Grupo_N;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClassroomsInfo extends HashData{

	public ClassroomsInfo() {
		super();
	}
	

	/**
	 * Creates a ClassroomsInfo object by reading data from a local file.
	 * 
	 * @param path The path to the local file.
	 * @return The created Schedule object.
	 */
	public static ClassroomsInfo createClassroomsInfoByLocalFile(String path) {

		ClassroomsInfo ci = new ClassroomsInfo();

		try {

			Scanner sc = new Scanner(new File(path));
			ci.readFile(sc);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return ci;
	}

}
