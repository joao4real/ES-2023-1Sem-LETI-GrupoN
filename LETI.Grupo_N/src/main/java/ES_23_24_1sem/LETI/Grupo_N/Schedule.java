package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * The Schedule class represents a schedule and provides methods to create and manipulate schedules.
 */
public class Schedule extends HashData {

	private LinkedHashMap<String, String> fieldMapping = new LinkedHashMap<>();
	private String mapping;

	  /**
     * Constructs a Schedule object with the specified mapping.
     *
     * @param mapping The mapping configuration for the schedule.
     */
	public Schedule(String mapping) {
		super();
		this.mapping = mapping;
	}

	 /**
     * Creates a Schedule object by reading data from a local file.
     *
     * @param path    The path to the local file.
     * @param mapping The mapping configuration for the schedule.
     * @return The created Schedule object.
     */
	public static Schedule createScheduleByLocalFile(String path, String mapping) {
		Schedule schedule = new Schedule(mapping);
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
     * @param urlStr  The URL of the remote file.
     * @param mapping The mapping configuration for the schedule.
     * @return The created Schedule object.
     */
	public static Schedule createScheduleByRemoteFile(String urlStr, String mapping) {
		Schedule schedule = new Schedule(mapping);
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
     * Reads data from the provided Scanner and updates the field mapping based on the configuration.
     *
     * @param sc The Scanner to read data from.
     */
	@Override
	public void readFile(Scanner sc) {
		super.readFile(sc);
		if (mapping.equals("X"))
			makeMappingManually(createMappingPanel());
		else
			makeMappingAutomatically(mapping);
		changeKeys();
	}

	 /**
     * Performs automatic field mapping based on the provided mapping string.
     *
     * @param mapping The mapping string.
     */
	private void makeMappingAutomatically(String mapping) {
		String[] s = mapping.split("\n");
		for (int i = 0; i < s.length; i++) {
			String[] x = s[i].split("->");
			fieldMapping.put(x[0], x[1]);
		}
	}

	/**
     * Performs manual field mapping using a GUI panel.
     *
     * @param panel The GUI panel for manual mapping.
     */
	private void makeMappingManually(JPanel panel) {
		if (showMappingDialog(panel) == JOptionPane.OK_OPTION)
			updateFieldMapping(panel);
		writeTextFile(fieldMapping);
	}

	/**
     * Writes the field mapping to a text file on the desktop.
     *
     * @param fieldMapping The field mapping to be written.
     */
	private void writeTextFile(LinkedHashMap<String, String> fieldMapping) {
		File f = new File(System.getProperty("user.home") + "\\Desktop\\ScheduleConfigurator.txt");
		try {
		PrintWriter writer = new PrintWriter(f);
		fieldMapping.forEach((k,v) -> writer.write(k + "->" + v + "\n"));
		writer.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Creates a GUI panel for manual mapping.
     *
     * @return The created GUI panel.
     */
	private JPanel createMappingPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 2));

		for (String variable : getVariables()) {
			JLabel label = new JLabel(variable);
			JTextField textField = new JTextField();
			panel.add(label);
			panel.add(textField);
		}

		return panel;
	}

	 /**
     * Displays a dialog for manual mapping and updates the field mapping accordingly.
     *
     * @param panel The GUI panel for manual mapping.
     * @return The result of the dialog (OK or Cancel).
     */
	private int showMappingDialog(JPanel panel) {
		return JOptionPane.showConfirmDialog(null, panel, "Mapeamento de Campos", JOptionPane.OK_CANCEL_OPTION);
	}

	/**
     * Updates the field mapping based on the values entered in the manual mapping dialog.
     *
     * @param panel The GUI panel for manual mapping.
     */
	private void updateFieldMapping(JPanel panel) {
		fieldMapping.clear();
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i += 2) {
			JLabel label = (JLabel) components[i];
			JTextField textField = (JTextField) components[i + 1];
			fieldMapping.put(label.getText(), textField.getText());
		}
	}

	/**
     * Gets the variables used in the schedule data.
     *
     * @return An array of variable names.
     */
	private String[] getVariables() {
		return new String[] { "Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana",
				"Hora início da aula", "Hora fim da aula", "Data da aula", "Características da sala pedida para a aula",
				"Sala atribuída à aula" };
	}

	/**
     * Gets the field mapping for this schedule.
     *
     * @return The field mapping.
     */
	public LinkedHashMap<String, String> getFieldMapping() {
		return fieldMapping;
	}

	 /**
     * Changes the keys in the schedule data based on the field mapping.
     */
	public void changeKeys() {
		LinkedHashMap<String, List<String>> dataMap = getMap();
		LinkedHashMap<String, List<String>> newDataMap = new LinkedHashMap<>();

		for (Map.Entry<String, String> entry : fieldMapping.entrySet()) {
			String newKey = entry.getKey();
			String oldKey = entry.getValue();

			if (dataMap.containsKey(oldKey)) {
				newDataMap.put(newKey, dataMap.get(oldKey));
			}
		}
		setMap(newDataMap);
		setLabels(getVariables());
	}
}
