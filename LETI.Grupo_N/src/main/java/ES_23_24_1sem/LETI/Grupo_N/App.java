package ES_23_24_1sem.LETI.Grupo_N;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
	/**
	 * Main method of the application.
	 * 
	 * @param args Command line arguments.
	 * @throws IOException If an input or output exception occurred.
	 */
	
	
	public static void main(String[] args) throws IOException {		
		
	
		JFrame frame = new JFrame("Schedule Analyser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 250);

		// Create label
		JLabel label = new JLabel("Please choose which option you would like to run");

		// Create two buttons
		JButton button1 = new JButton("Visualize imported schedule");
		JButton button2 = new JButton("Evaluate schedule qualitatively");

		// Create a panel with GridBagLayout to center the buttons with space
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 0, 10, 0); // Add space (top, left, bottom, right)

		panel.add(label, c);

		c.anchor = GridBagConstraints.CENTER;

		// Add the first button
		panel.add(button1, c);

		// Move to the next row
		c.gridy = 1; // Corrected to 1

		// Add the second button
		panel.add(button2, c);

		// Set the panel as the content pane of the frame
		frame.setContentPane(panel);

		// Set frame properties
		frame.setLocationRelativeTo(null); // Center the frame on the screen
		frame.setVisible(true);
		
	}
		/*JFrame frame = new JFrame();
		frame.setSize(500, 500);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton button1 = new JButton("Visualize Schedule");
		c.anchor = GridBagConstraints.CENTER;
		panel.add(button1, c);
		
		JButton button2 = new JButton("Evaluate the schedule qualitatively");
		c.anchor = GridBagConstraints.PAGE_END;
		panel.add(button2,c);
			

		JTextField optionField = new JTextField(5);
		JLabel optionLabel = new JLabel("Do you want to read local or remote file? (l/r): ");
		c.anchor = GridBagConstraints.PAGE_START;
		panel.add(optionLabel, c);
		c.gridx = 2;
		panel.add(optionField, c);

		JTextField pathField = new JTextField(20);
		JLabel pathLabel = new JLabel("Path or URL: ");
		c.gridy = 1;
		c.gridx = 1;
		panel.add(pathLabel, c);
		c.gridx = 2;
		panel.add(pathField, c);

		frame.add(panel);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				
				String option = optionField.getText();
				String path = JOptionPane.showInputDialog(frame, "Type the path for local file", null);
				Schedule schedule = new Schedule();
			
				switch (option) {
				case "l":
					
					schedule = Schedule.createScheduleByLocalFile(path);
					 
					break;

				case "r":
					schedule = Schedule.createScheduleByRemoteFile(path);
					break;

				default:
					System.err.println("Invalid Option");
					return;
				}

				File htmlFile;
				try {
					htmlFile = createHTMLFile(schedule);
					openWebPage(htmlFile);
					Thread.sleep(5000); // Sleep for 5 seconds (adjust the time as needed)
					if (htmlFile.delete())
						System.out.println("HTML file deleted successfully.");
					else
						System.err.println("Failed to delete HTML file.");

				} catch (IOException e) {
					e.printStackTrace();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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