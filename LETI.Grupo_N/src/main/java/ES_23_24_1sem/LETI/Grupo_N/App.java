package ES_23_24_1sem.LETI.Grupo_N;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Main class of the application.
 */
public class App {
	 /**
     * Main method of the application.
     * @param args Command line arguments.
     * @throws IOException If an input or output exception occurred.
     */
	public static void main(String[] args) throws IOException {

		Schedule schedule = new Schedule();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to read local or remote archives? (l/r): ");

		switch (scanner.nextLine()) {

		case "l":
			System.out.println("Type the path for local archive: ");
			schedule = Schedule.createScheduleByLocalFile(scanner.nextLine());
			break;

		case "r":
			System.out.println("Type the URL for remote archive: ");
			schedule = Schedule.createScheduleByRemoteFile(scanner.nextLine());
			break;

		default:
			System.err.println("Invalid Option");
			break;
		}
		scanner.close();

		File htmlFile = createHTMLFile(schedule);
		Desktop.getDesktop().browse(htmlFile.toURI());
		try {
		    Thread.sleep(5000); // Sleep for 5 seconds (adjust the time as needed)
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		// After the delay, delete the file
		if (htmlFile.delete()) {
		    System.out.println("HTML file deleted successfully.");
		} else {
		    System.err.println("Failed to delete HTML file.");
		}
	}

	// Create HTML file
	/**
     * Creates an HTML file.
     * @param schedule The schedule to be written to the HTML file.
     * @return The created HTML file.
     * @throws IOException If an input or output exception occurred.
     */
	private static File createHTMLFile(Schedule schedule) throws IOException {

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
					+ "				initialSort:[{column:\"building\",dir:\"asc\"},],\r\n"
					+ "				columns:[\r\n"
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