package ES_23_24_1sem.LETI.Grupo_N;

import java.io.File;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		Schedule schedule = new Schedule();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to read local or remote archives? (l/r): ");

		switch (scanner.nextLine()) {

		case "l":
			System.out.println("Type the path for local archive: ");
			schedule = Schedule.createScheduleByLocalFile(scanner.nextLine());
			System.out.println(schedule.getMap().get("Unidade Curricular"));
			break;

		case "r":
			System.out.println("Type the URL for remote archive: ");
			schedule = Schedule.createScheduleByRemoteFile(scanner.nextLine());
			System.out.println(schedule.getMap().get("Unidade Curricular"));
			break;

		default:
			System.out.println("Invalid Option");
			break;
		}

		scanner.close();

		//Create HTML file
		
		private File createHTMLFile(Schedule schedule) {
			
			String path = "C:" + File.separator + "hello" + File.separator + "hi.txt";
			// Use relative path for Unix systems
			File f = new File(path);

			f.getParentFile().mkdirs(); 
			f.createNewFile();
			
		
			StringBuilder sb = new StringBuilder("<!DOCTYPE html>\r\n"
					+ "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
					+ "	<head>\r\n"
					+ "		<meta charset=\"utf-8\" />\r\n"
					+ "		<link href=\"https://unpkg.com/tabulator-tables@4.8.4/dist/css/tabulator.min.css\" rel=\"stylesheet\">\r\n"
					+ "		<script type=\"text/javascript\" src=\"https://unpkg.com/tabulator-tables@4.8.4/dist/js/tabulator.min.js\"></script>\r\n"
					+ "	</head>\r\n"
					+ "	<body>\r\n"
					+ "		<H1>Horário</H1>	\r\n"
					+ "		<div id=\"example-table\"></div>\r\n"
					+ "\r\n"
					+ "		<script type=\"text/javascript\">\r\n"
					+ "\r\n"
					+ "			var tabledata = [");
			
			for(int i = 0; i < schedule.getMap().get("Curso").size(); i++) {
				String dayInfo = "{";
				for (String key : schedule.getMap().keySet())
					switch(key) {
					case "Curso":
						dayInfo += "course:\"" + schedule.getMap().get(key).get(0) + "\"";
						break;
					case "Unidade Curricular":
						dayInfo += "curricularunit:\"" + schedule
						break;
					case "Turno":
						dayInfo += "shift:\"" + schedule
						break;
					case "Turma":
						dayInfo += "class:\"" + schedule
						break;
					case "Inscritos no turno":
						dayInfo += "registeredonshift:\"" + schedule
						break;
					case "Dia da semana":
						dayInfo += "weekday:\"" + schedule
						break;
					case "Hora início da aula":
						dayInfo += "lessonstarttime:\"" + schedule
						break;
					case "Hora fim da aula":
						dayInfo += "lessonendtime:\"" + schedule
						break;
					case "Data da aula":
						dayInfo += "lessonday:\"" + schedule
						break;
					case "Características da sala pedida para a aula":
						dayInfo += "classroomrequirements:\"" + schedule
						break;
					case "Sala atribuída à aula":
						dayInfo += "classroomgiven:\"" + schedule
						break;
						
					}
			
		}
		
		
		
		
		
		
		
	}
}