package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Schedule extends HashData {

	private LinkedHashMap<String, String> fieldMapping = new LinkedHashMap<>();

	public Schedule() {
		super();
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

	@Override
	public void readFile(Scanner sc) {
		super.readFile(sc);
		JPanel panel = createMappingPanel();
		if (showMappingDialog(panel) == JOptionPane.OK_OPTION)
			updateFieldMapping(panel);
		else
			return;
		changeKeys();
	}

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

	private int showMappingDialog(JPanel panel) {
		return JOptionPane.showConfirmDialog(null, panel, "Mapeamento de Campos", JOptionPane.OK_CANCEL_OPTION);
	}

	private void updateFieldMapping(JPanel panel) {
		fieldMapping.clear();
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i += 2) {
			JLabel label = (JLabel) components[i];
			JTextField textField = (JTextField) components[i + 1];
			fieldMapping.put(label.getText(), textField.getText());
		}
	}

	private String[] getVariables() {
		return new String[] { "Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana",
				"Hora início da aula", "Hora fim da aula", "Data da aula", "Características da sala pedida para a aula",
				"Sala atribuída à aula" };
	}

	public LinkedHashMap<String, String> getFieldMapping() {
		return fieldMapping;
	}

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
