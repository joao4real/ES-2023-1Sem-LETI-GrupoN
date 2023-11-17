package ES_23_24_1sem.LETI.Grupo_N;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
	 * Main method of the application.
	 * 
	 * @param args Command line arguments.
	 * @throws IOException If an input or output exception occurred.
	 */

	public static void main(String[] args) throws IOException {

		frame = new JFrame("Schedule Analyser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 250);

		// Create two buttons
		JButton button1 = new JButton("Visualize imported schedule");
		JButton button2 = new JButton("Evaluate schedule qualitatively");

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

	

		ClassroomsInfo ci = ClassroomsInfo.createClassroomsInfoByLocalFile("D:\\Joao\\Downloads\\CaracterizacaoDasSalas.csv");
		Schedule d = Schedule.createScheduleByLocalFile("D:\\Joao\\Downloads\\HorarioDeExemplo.csv");

		List<Integer> overCapacity = new ArrayList<>();
		List<Boolean> matchRequirements = new ArrayList<>();
		List<Integer> featuresNotUsed = new ArrayList<>();
		List<Boolean> classWithoutRoom = new ArrayList<>();

		analyse(d, ci, overCapacity, matchRequirements, featuresNotUsed, classWithoutRoom);
	}

	/*
	 * Return the result of the main queries
	 */
	private static void analyse(Schedule sc, ClassroomsInfo ci, List<Integer> overCapacity,
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

	private static boolean isClassWithoutRoom(LinkedHashMap<String, List<String>> scMap, int i) {
		return (!scMap.get("Características da sala pedida para a aula").get(i).equals("Não necessita de sala")
				&& scMap.get("Sala atribuída à aula").get(i).equals("N/A")) ? true : false;
	}

	private static boolean matchRequirements(List<String> roomRequirements, List<String> roomFeatures) {
		if (roomRequirements == null)
			return true;

		List<String> remainingRequirements = new ArrayList<>(roomRequirements);
		List<String> remainingFeatures = new ArrayList<>(roomFeatures);

		Iterator<String> featureIterator = remainingFeatures.iterator();
		while (featureIterator.hasNext()) {
			String feature = featureIterator.next();
			if (remainingRequirements.remove(feature)) {
				featureIterator.remove();
			}
		}

		roomFeatures.clear();
		roomFeatures.addAll(remainingFeatures);
		return remainingRequirements.isEmpty();
	}

	private static List<String> getRoomRequirements(LinkedHashMap<String, List<String>> scMap, int i) {
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
						openWebPage(createHTMLFile(Schedule.createScheduleByLocalFile(input)));
				}
				break;

			case "r":
				input = JOptionPane.showInputDialog(frame, "Type the URL for remote file", null);
				if (input != null) {
					if (!input.isEmpty())
						openWebPage(createHTMLFile(Schedule.createScheduleByRemoteFile(input)));
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

	private static void openWebPage(File htmlFile) {
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Create HTML file
	/**
	 * Creates an HTML file.
	 * 
	 * @param schedule The schedule to be written to the HTML file.
	 * @return The created HTML file.
	 * @throws IOException If an input or output exception occurred.
	 */
	public static File createHTMLFile(Schedule schedule) throws IOException {

		String path = System.getProperty("user.home") + "\\Desktop\\SalasDeAulaPorTiposDeSala1.html";
		File f = new File(path);

		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>\r\n" + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
				+ "	<head>\r\n" + "		<meta charset=\"utf-8\" />\r\n"
				+ "		<link href=\"https://unpkg.com/tabulator-tables@4.8.4/dist/css/tabulator.min.css\" rel=\"stylesheet\">\r\n"
				+ "		<script type=\"text/javascript\" src=\"https://unpkg.com/tabulator-tables@4.8.4/dist/js/tabulator.min.js\"></script>\r\n"
				+ "	</head>\r\n" + "	<body>\r\n" + "		<H1>Horário</H1>	\r\n"
				+ "		<div id=\"example-table\"></div>\r\n" + "\r\n" + "		<script type=\"text/javascript\">\r\n"
				+ "\r\n" + "			var tabledata = [\n");

		for (int i = 0; i < schedule.getMap().get("Curso").size(); i++) {
			String dayInfo = "{";
			for (String key : schedule.getMap().keySet()) {
				switch (key) {
				case "Curso":
					dayInfo += "course:\"";
					break;
				case "Unidade Curricular":
					dayInfo += "curricularunit:\"";
					break;
				case "Turno":
					dayInfo += "shift:\"";
					break;
				case "Turma":
					dayInfo += "class:\"";
					break;
				case "Inscritos no turno":
					dayInfo += "registeredonshift:\"";
					break;
				case "Dia da semana":
					dayInfo += "weekday:\"";
					break;
				case "Hora início da aula":
					dayInfo += "lessonstarttime:\"";
					break;
				case "Hora fim da aula":
					dayInfo += "lessonendtime:\"";
					break;
				case "Data da aula":
					dayInfo += "lessonday:\"";
					break;
				case "Características da sala pedida para a aula":
					dayInfo += "classroomrequirements:\"";
					break;
				case "Sala atribuída à aula":
					dayInfo += "classroomgiven:\"";
					break;
				}
				dayInfo += schedule.getMap().get(key).get(i) + "\", ";
			}
			sb.append(dayInfo + "},\n");
		}
		sb.append("];\r\n" + "			\r\n" + "			var table = new Tabulator(\"#example-table\", {\r\n"
				+ "				data:tabledata,\r\n" + "				layout:\"fitDatafill\",\r\n"
				+ "				pagination:\"local\",\r\n" + "				paginationSize:10,\r\n"
				+ "				paginationSizeSelector:[5, 10, 20, 40],\r\n"
				+ "				movableColumns:true,\r\n" + "				paginationCounter:\"rows\",\r\n"
				+ "				initialSort:[{column:\"building\",dir:\"asc\"},],\r\n" + "				columns:[\r\n"
				+ "					{title:\"Curso\", field:\"course\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Unidade Curricular\", field:\"curricularunit\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Turno\", field:\"shift\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Turma\", field:\"class\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Inscritos no turno\", field:\"registeredonshift\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Dia da semana\", field:\"weekday\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Hora início da aula\", field:\"lessonstarttime\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Hora fim da aula\", field:\"lessonendtime\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Data da aula\", field:\"lessonday\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Características da sala pedida para a aula\", field:\"classroomrequirements\", headerFilter:\"input\"},\r\n"
				+ "					{title:\"Sala atribuída à aula\", field:\"classroomgiven\", headerFilter:\"input\"},\r\n"
				+ "					\r\n" + "				],\r\n" + "			});\r\n" + "		</script>\r\n"
				+ "		\r\n" + "	</body>\r\n" + "</html>\r\n");

		// Write the HTML content to a file
		try {
			PrintWriter writer = new PrintWriter(f);
			writer.println(sb.toString());
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}
}