package ES_23_24_1sem.LETI.Grupo_N;

import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Main class of the application.
 */
public class App {

	private static JFrame frame;
	private static Schedule schedule;
	private static ClassroomsInfo database;

	public static void main(String[] args) throws IOException {

		frame = new JFrame("Schedule Analyser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 250);
		
		database = ClassroomsInfo.createClassroomsInfoByRemoteFile("https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/CaracterizacaoSalas.csv");

		JPanel cardPanel = new JPanel(new CardLayout());

		JPanel importPanel = createImportPanel(cardPanel);
		JPanel optionsPanel = createOptionsPanel(cardPanel);
		JPanel evaluatePanel = createEvaluatePanel(cardPanel);
		JPanel customMetricsPanel = createCustomMetricsPanel(cardPanel);

		cardPanel.add(importPanel, "IMPORT_PANEL");
		cardPanel.add(optionsPanel, "OPTIONS_PANEL");
		cardPanel.add(evaluatePanel, "EVALUATE_PANEL");
		cardPanel.add(customMetricsPanel, "CUSTOM_PANEL");

		CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
		cardLayout.show(cardPanel, "IMPORT_PANEL");

		frame.add(cardPanel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static JPanel createImportPanel(JPanel cardPanel) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 0, 10, 0);

		panel.add(new JLabel("Select which way you want to import schedule:"), c);

		JButton localButton = new JButton("Local");
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(localButton, c);

		JButton remoteButton = new JButton("Remote");
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_END;
		panel.add(remoteButton, c);

		localButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				schedule = getSchedule("l");
				if (getSchedule() != null)
					((CardLayout) cardPanel.getLayout()).show(cardPanel, "OPTIONS_PANEL");
			}
		});

		remoteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				schedule = getSchedule("r");
				if (getSchedule() != null)
					((CardLayout) cardPanel.getLayout()).show(cardPanel, "OPTIONS_PANEL");
			}
		});

		return panel;
	}

	private static JPanel createOptionsPanel(JPanel cardPanel) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton backButton = new JButton("Back");
		JButton showScheduleButton = new JButton("Show Schedule");
		JButton evaluateScheduleButton = new JButton("Evaluate Schedule");

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 0, 10, 0);

		panel.add(new JLabel("Select what do you want to do with the schedule:"), c);

		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(showScheduleButton, c);

		c.gridy = 2;
		panel.add(evaluateScheduleButton, c);

		c.gridy = 3;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		panel.add(backButton, c);

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "IMPORT_PANEL");
			}
		});

		evaluateScheduleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "EVALUATE_PANEL");
			}
		});

		showScheduleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					openWebPage(HTMLFileCreator.createSchedule(getSchedule()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		return panel;
	}

	private static JPanel createEvaluatePanel(JPanel cardPanel) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton backButton = new JButton("Back");
		JButton evaluateWithDefaultMetricsButton = new JButton("Evaluate with default metrics");
		JButton evaluateWithCustomMetricsButton = new JButton("Evaluate with custom metrics");

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 0, 10, 0);

		panel.add(new JLabel("Select which way you want to evaluate the schedule:"), c);

		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(evaluateWithDefaultMetricsButton, c);

		c.gridy = 2;
		panel.add(evaluateWithCustomMetricsButton, c);

		c.gridy = 3;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		panel.add(backButton, c);

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "OPTIONS_PANEL");
			}
		});

		evaluateWithDefaultMetricsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				List<Integer> overCapacity = new ArrayList<>();
				List<Boolean> matchRequirements = new ArrayList<>();
				List<Integer> featuresNotUsed = new ArrayList<>();
				List<Boolean> classWithoutRoom = new ArrayList<>();
				
				database = ClassroomsInfo.createClassroomsInfoByLocalFile("D:\\Joao\\Downloads\\CaracterizaçãoDasSalas.csv");

				analyse(getSchedule(), database, overCapacity, matchRequirements, featuresNotUsed, classWithoutRoom);
				openWebPage(HTMLFileCreator.createScheduleEvaluator(getSchedule(), overCapacity, matchRequirements,
						featuresNotUsed, classWithoutRoom));
			}
		});

		evaluateWithCustomMetricsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "CUSTOM_PANEL");
			}
		});
		return panel;
	}
	
	private static JPanel createCustomMetricsPanel(JPanel cardPanel) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton backButton = new JButton("Back");

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 0, 10, 0);

		panel.add(new JLabel("Make your own metrics with the following fields:"), c);

		JButton makeQuery = new JButton("Make query");
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(makeQuery);
		
		makeQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
			}
		});

		c.gridy = 2;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		panel.add(backButton, c);

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "EVALUATE_PANEL");
			}
		});

	
		return panel;
	}
	
	public static void analyse(Schedule sc, ClassroomsInfo ci, List<Integer> overCapacity,
			List<Boolean> matchRequirements, List<Integer> featuresNotUsed, List<Boolean> classWithoutRoom) {

		LinkedHashMap<String, List<String>> scMap = sc.getMap();
		LinkedHashMap<String, List<String>> ciMap = ci.getMap();

		for (int i = 0; i < scMap.get("Sala atribuída à aula").size(); i++) {

			String givenRoom = scMap.get("Sala atribuída à aula").get(i);
			List<String> roomReqs = getRoomRequirements(scMap, i);
			List<String> roomFeats = getRoomFeatures(ciMap, givenRoom);

			overCapacity.add(getOverCapacity(scMap, ciMap, i, givenRoom));
			matchRequirements.add(matchRequirements(roomReqs, roomFeats));
			featuresNotUsed.add(roomFeats.size());
			classWithoutRoom.add(isClassWithoutRoom(scMap, i));
		}
	}

	public static boolean isClassWithoutRoom(LinkedHashMap<String, List<String>> scMap, int i) {
		return (!scMap.get("Características da sala pedida para a aula").get(i).equals("Não necessita de sala")
				&& scMap.get("Sala atribuída à aula").get(i).equals("N/A")) ? true : false;
	}

	public static boolean matchRequirements(List<String> roomRequirements, List<String> roomFeatures) {
		if (roomRequirements == null)
			return true;

		List<String> remainingRequirements = new ArrayList<>(roomRequirements);
		List<String> remainingFeatures = new ArrayList<>(roomFeatures);

		Iterator<String> featureIterator = remainingFeatures.iterator();
		while (featureIterator.hasNext()) {
			String feature = featureIterator.next();
			if (remainingRequirements.remove(feature)) {
				featureIterator.remove();
				roomFeatures.clear();
				roomFeatures.addAll(remainingFeatures);
				return true;

			}
		}
		return false;
	}

	public static List<String> getRoomRequirements(LinkedHashMap<String, List<String>> scMap, int i) {
		return (scMap.get("Características da sala pedida para a aula").get(i).equals("Não necessita de sala")) ? null
				: Arrays.asList(scMap.get("Características da sala pedida para a aula").get(i).split("/"));
	}

	private static List<String> getRoomFeatures(LinkedHashMap<String, List<String>> ciMap, String givenRoom) {

		List<String> features = new ArrayList<>();

		int index = ciMap.get("Nome sala").indexOf(givenRoom);

		if (index != -1) {
			for (String key : ciMap.keySet()) {
				if (ciMap.get(key).get(index).equals("X"))
					features.add(key);
			}
		}
		return features;
	}

	private static int getOverCapacity(LinkedHashMap<String, List<String>> scMap,
			LinkedHashMap<String, List<String>> ciMap, int i, String givenRoom) {

		// Index of room line in database
		int index = ciMap.get("Nome sala").indexOf(givenRoom);

		return (index != -1) ? Math.max(Integer.parseInt(scMap.get("Inscritos no turno").get(i))
				- Integer.parseInt(ciMap.get("Capacidade Normal").get(index)), 0) : 0;
	}

	private static Schedule getSchedule(String option) {
		String input;

		switch (option) {
		case "l":
			input = JOptionPane.showInputDialog(frame, "Type the path for local file", null);
			if (input != null && !input.isEmpty()) {
				return Schedule.createScheduleByLocalFile(input);
			} else {
				return null;
			}
		case "r":
			input = JOptionPane.showInputDialog(frame, "Type the URL for remote file", null);
			if (input != null && !input.isEmpty()) {
				return Schedule.createScheduleByRemoteFile(input);
			} else {
				return null;
			}
		default:
			System.err.println("Invalid Option");
			return null;
		}
	}

	public static void openWebPage(File htmlFile) {
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
			Thread.sleep(500);
			if (htmlFile.delete())
				System.out.println("HTML file deleted successfully!");
			else
				System.out.println("Failed to delete HTML file!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException x) {
			x.printStackTrace();
		}
	}

	public static Schedule getSchedule() {
		return schedule;
	}

	public static ClassroomsInfo getDatabase() {
		return database;
	}
}