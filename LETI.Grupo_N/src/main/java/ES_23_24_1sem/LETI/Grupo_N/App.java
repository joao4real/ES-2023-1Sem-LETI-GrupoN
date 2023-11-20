package ES_23_24_1sem.LETI.Grupo_N;

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
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Main class of the application.
 */
public class App {

	private static Stack<JPanel> menuStack = new Stack<>();
	private static JFrame frame;

	/**
	 * The main method of the Schedule Analyser application. It creates a JFrame
	 * with two buttons for visualizing an imported schedule and evaluating a
	 * schedule qualitatively. It also sets up action listeners for the buttons to
	 * handle user interactions.
	 * 
	 * @param args command-line arguments (not used)
	 * @throws IOException if there is an error reading the local files
	 */

	public static void main(String[] args) throws IOException {

		frame = new JFrame("Schedule Analyser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 250);

		// Create two buttons
		JButton button1 = new JButton("Visualize schedule");
		JButton button2 = new JButton("Evaluate schedule");

		// Create a panel with GridBagLayout to center the buttons with space between
		// them
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 0, 10, 0);

		panel.add(new JLabel("Please choose which option you would like to run"), c);

		// Move to the next row
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER; // Set anchor to CENTER for the buttons

		panel.add(button1, c);

		// Move to the next row
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_END;

		panel.add(button2, c);

		// Set the panel as the content pane of the frame
		menuStack.push(panel);
		frame.setContentPane(panel);

		// Set frame properties
		frame.setLocationRelativeTo(null); // Center the frame on the screen
		frame.setVisible(true);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel panel = new JPanel(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();

				c.gridx = 0;
				c.gridy = 0;
				c.anchor = GridBagConstraints.PAGE_START;
				c.insets = new Insets(10, 0, 10, 0);

				panel.add(new JLabel("Do you want to run local or remote file?"), c);

				JButton button3 = new JButton("Local");
				JButton button4 = new JButton("Remote");
				JButton button5 = new JButton("Back");

				// Add Local button
				c.gridy = 1;
				c.anchor = GridBagConstraints.CENTER; // Set anchor to CENTER for the buttons
				panel.add(button3, c);

				// Add Remote button
				c.gridy = 2;
				panel.add(button4, c);

				// Add Back button
				c.gridy = 3;
				c.anchor = GridBagConstraints.LAST_LINE_END;
				panel.add(button5, c);
				menuStack.push(panel);

				button3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						openSchedule("l", frame, panel);
					}
				});

				button4.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						openSchedule("r", frame, panel);
					}
				});

				button5.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						frame.setContentPane(menuStack.get(0));
					}
				});

				// Set the panel as the content pane of the frame
				frame.setContentPane(panel);
				frame.setVisible(true);
			}
		});

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ClassroomsInfo ci = ClassroomsInfo
						.createClassroomsInfoByLocalFile("C:\\Users\\Joao\\Downloads\\CaracterizaçãoDasSalas.csv");
				Schedule d = Schedule.createScheduleByLocalFile("C:\\Users\\Joao\\Downloads\\HorarioDeExemplo.csv");

				List<Integer> overCapacity = new ArrayList<>();
				List<Boolean> matchRequirements = new ArrayList<>();
				List<Integer> featuresNotUsed = new ArrayList<>();
				List<Boolean> classWithoutRoom = new ArrayList<>();

				analyse(d, ci, overCapacity, matchRequirements, featuresNotUsed, classWithoutRoom);
				openWebPage(HTMLFileCreator.createScheduleEvaluator(d, overCapacity, matchRequirements, featuresNotUsed,
						classWithoutRoom));
			}

		});

	}

	/**
	 * This method analyses the given schedule and classrooms information. It checks
	 * for overcapacity, matches requirements, unused features, and classes without
	 * rooms. The results are stored in the provided lists.
	 * 
	 * @param sc                the schedule to be analysed
	 * @param ci                the classrooms information
	 * @param overCapacity      a list to store the overcapacity results
	 * @param matchRequirements a list to store the match requirements results
	 * @param featuresNotUsed   a list to store the unused features results
	 * @param classWithoutRoom  a list to store the classes without rooms results
	 */
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

	private static void openSchedule(String option, JFrame frame, JPanel panel) {

		panel.add(new JTextField(5));
		String input;

		try {
			switch (option) {

			case "l":
				input = JOptionPane.showInputDialog(frame, "Type the path for local file", null);
				if (input != null) {
					if (!input.isEmpty())
						openWebPage(HTMLFileCreator.createSchedule(Schedule.createScheduleByLocalFile(input)));
				}
				break;

			case "r":
				input = JOptionPane.showInputDialog(frame, "Type the URL for remote file", null);
				if (input != null) {
					if (!input.isEmpty())
						openWebPage(HTMLFileCreator.createSchedule(Schedule.createScheduleByRemoteFile(input)));
				}
				break;

			default:
				System.err.println("Invalid Option");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void openWebPage(File htmlFile) {
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
			Thread.sleep(5000);
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

}