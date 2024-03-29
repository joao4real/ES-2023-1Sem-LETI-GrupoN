package ES_23_24_1sem.LETI.Grupo_N;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Class representing a ClassroomsInfo.
 */

public class ClassroomsInfo extends HashData {

	/**
	 * Default constructor for the ClassroomsInfo class.
	 */

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

	/**
	 * Creates a ClassroomsInfo object by reading data from a remote file.
	 * 
	 * @param urlStr The URL of the remote file.
	 * @return The created Schedule object.
	 */
	public static ClassroomsInfo createClassroomsInfoByRemoteFile(String urlStr) {

		ClassroomsInfo classroomsInfo = new ClassroomsInfo();

		try {

			InputStream in = new URL(urlStr).openStream();
			classroomsInfo.readFile(new Scanner(in));
			in.close();

		} catch (MalformedURLException e) {
			System.err.println("Invalid URL: " + urlStr);
			e.printStackTrace();

		} catch (IOException e) {
			System.err.println("Failed to open the remote file: " + urlStr);
			e.printStackTrace();
		}

		return classroomsInfo;
	}	
}
