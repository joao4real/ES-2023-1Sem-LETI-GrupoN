package ES_23_24_1sem.LETI.Grupo_N;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;

public class HTMLFileCreator {

	public static File createSchedule(Schedule schedule) throws IOException {

		String path = System.getProperty("user.home") + "\\Desktop\\Schedule.html";
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

		write(f, sb);
		return f;
	}

	public static File createScheduleEvaluator(Schedule schedule, List<Integer> overCapacity,
			List<Boolean> matchRequirements, List<Integer> featuresNotUsed, List<Boolean> classWithoutRoom) {

		String path = System.getProperty("user.home") + "\\Desktop\\ScheduleEvaluator.html";
		File f = new File(path);

		LinkedHashMap<String, List<String>> map = schedule.getMap();
		StringBuilder sb = new StringBuilder();

		sb.append("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n" + "<style>\r\n" + "  table {\r\n"
				+ "    font-family: arial, sans-serif;\r\n" + "    border-collapse: collapse;\r\n"
				+ "    width: 100%;\r\n" + "  }\r\n" + "\r\n" + "  td, th {\r\n" + "    border: 1px solid #dddddd;\r\n"
				+ "    text-align: center;\r\n" + "    padding: 8px;\r\n" + "  }\r\n" + "\r\n" + "  .pagination {\r\n"
				+ "    display: flex;\r\n" + "    list-style: none;\r\n"
				+ "    margin: 20px 0; /* Add margin for spacing */\r\n" + "  }\r\n" + "\r\n" + "  .pagination li {\r\n"
				+ "    margin-right: 5px;\r\n" + "    cursor: pointer;\r\n" + "    background-color: #4CAF50;\r\n"
				+ "    color: white;\r\n" + "    padding: 8px 12px;\r\n" + "    border-radius: 4px;\r\n"
				+ "    transition: background-color 0.3s ease, opacity 0.3s ease;\r\n" + "  }\r\n" + "\r\n"
				+ "  .pagination li:hover {\r\n" + "    background-color: #45a049;\r\n"
				+ "    opacity: 0.8; /* Adjust the opacity value as needed */\r\n" + "  }\r\n" + "\r\n"
				+ "  .pagination-buttons {\r\n" + "    display: flex;\r\n" + "    justify-content: space-between;\r\n"
				+ "    margin: 12px;\r\n" + "  }\r\n" + "\r\n" + "  .pagination-buttons button {\r\n"
				+ "    cursor: pointer;\r\n" + "    padding: 8px 12px;\r\n" + "    border-radius: 4px;\r\n"
				+ "    background-color: #4CAF50;\r\n" + "    color: white;\r\n" + "    border: none;\r\n"
				+ "    transition: background-color 0.3s ease;\r\n" + "  }\r\n" + "\r\n"
				+ "  .pagination-buttons button:hover {\r\n" + "    background-color: #45a049;\r\n" + "  }\r\n"
				+ "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "\r\n" + "<h2>Schedule Evaluator</h2>\r\n" + "\r\n"
				+ "<table id=\"myTable\">\r\n" + "  <tr style=\"background-color: #88a0a4;\">\r\n"
				+ "    <th>Unidade Curricular</th>\r\n" + "<th>Turno</th>\r\n" + "<th>Turma</th>\r\n"
				+ "<th>Dia da Semana</th>\r\n" + "<th>Hora início da aula</th>\r\n" + "<th>Hora fim da aula</th>\r\n"
				+ "<th>Data da aula</th>\r\n" + "<th>Sobrelotação (%)</th>\r\n" + "<th>Cumpre requisitos</th>\r\n"
				+ "<th>Número de características desperdiçadas</th>\r\n"
				+ "<th>Possui sala atribuída (caso seja requisitada sala)</th>\r\n" + "</tr>");

		for (int i = 0; i < schedule.getMap().get("Unidade Curricular").size(); i++) {
			sb.append("<tr>\r\n" + "<td>" + map.get("Unidade Curricular").get(i) + "</td>\r\n" + "<td>"
					+ map.get("Turno").get(i) + "</td>\r\n" + "<td>" + map.get("Turma").get(i) + "</td>\r\n" + "<td>"
					+ map.get("Dia da semana").get(i) + "</td>\r\n" + "<td>" + map.get("Hora início da aula").get(i)
					+ "</td>\r\n" + "<td>" + map.get("Hora fim da aula").get(i) + "</td>\r\n" + "<td>"
					+ map.get("Data da aula").get(i) + "</td>\r\n" + "<td>");

			sb.append(
					(Integer.parseInt(map.get("Inscritos no turno").get(i)) > 0)
							? (int) (((double) overCapacity.get(i)
									/ (double) Integer.parseInt(map.get("Inscritos no turno").get(i))) * 100)
							: 0);
			sb.append("</td>\r\n");
			sb.append(matchRequirements.get(i) ? "<td>&#10003;</td>\r\n" : "<td>&#10005;</td>\r\n");
			sb.append("<td>" + featuresNotUsed.get(i) + "</td>\r\n");
			sb.append(classWithoutRoom.get(i) ? "<td>&#10005;</td>\r\n" : "<td>&#10003;</td>\r\n");
			sb.append("</tr>\r\n");
		}
		sb.append("</table>\r\n" + "\r\n" + "<div class=\"pagination\" id=\"pagination\">\r\n" + "</div>\r\n" + "\r\n"
				+ "<div class=\"pagination-buttons\" id=\"paginationButtons\">\r\n" + "</div>\r\n" + "\r\n"
				+ "<script>\r\n" + "document.addEventListener(\"DOMContentLoaded\", function () {\r\n"
				+ "  var table = document.getElementById(\"myTable\");\r\n"
				+ "  var rowsPerPage = 20; // Set the number of rows per page\r\n"
				+ "  var totalPages = Math.ceil(table.rows.length / rowsPerPage);\r\n" + "  var currentPage = 1;\r\n"
				+ "\r\n" + "  // Create pagination controls\r\n"
				+ "  var pagination = document.getElementById(\"pagination\");\r\n"
				+ "  var paginationButtons = document.getElementById(\"paginationButtons\");\r\n" + "\r\n"
				+ "  function updatePagination() {\r\n" + "    pagination.innerHTML = \"\";\r\n"
				+ "    for (var i = Math.max(1, currentPage - 2); i <= Math.min(currentPage + 3, totalPages); i++) {\r\n"
				+ "      var li = document.createElement(\"li\");\r\n" + "      li.textContent = i;\r\n"
				+ "      li.addEventListener(\"click\", function () {\r\n"
				+ "        currentPage = parseInt(this.textContent);\r\n" + "        showPage(currentPage);\r\n"
				+ "        updatePagination();\r\n" + "      });\r\n" + "      pagination.appendChild(li);\r\n"
				+ "    }\r\n" + "\r\n" + "    // Pagination buttons logic\r\n"
				+ "    var backButton = createButton(\"Back\", function () {\r\n" + "      if (currentPage > 1) {\r\n"
				+ "        currentPage--;\r\n" + "        showPage(currentPage);\r\n"
				+ "        updatePagination();\r\n" + "      }\r\n" + "    });\r\n" + "\r\n"
				+ "    var nextButton = createButton(\"Next\", function () {\r\n"
				+ "      if (currentPage < totalPages) {\r\n" + "        currentPage++;\r\n"
				+ "        showPage(currentPage);\r\n" + "        updatePagination();\r\n" + "      }\r\n"
				+ "    });\r\n" + "\r\n" + "    var firstButton = createButton(\"First\", function () {\r\n"
				+ "      if (currentPage !== 1) {\r\n" + "        currentPage = 1;\r\n"
				+ "        showPage(currentPage);\r\n" + "        updatePagination();\r\n" + "      }\r\n"
				+ "    });\r\n" + "\r\n" + "    var lastButton = createButton(\"Last\", function () {\r\n"
				+ "      if (currentPage !== totalPages) {\r\n" + "        currentPage = totalPages;\r\n"
				+ "        showPage(currentPage);\r\n" + "        updatePagination();\r\n" + "      }\r\n"
				+ "    });\r\n" + "\r\n" + "    paginationButtons.innerHTML = \"\";\r\n"
				+ "    paginationButtons.appendChild(firstButton);\r\n"
				+ "    paginationButtons.appendChild(backButton);\r\n"
				+ "    paginationButtons.appendChild(nextButton);\r\n"
				+ "    paginationButtons.appendChild(lastButton);\r\n" + "  }\r\n" + "\r\n"
				+ "  function createButton(text, clickHandler) {\r\n"
				+ "    var button = document.createElement(\"button\");\r\n" + "    button.textContent = text;\r\n"
				+ "    button.addEventListener(\"click\", clickHandler);\r\n" + "    return button;\r\n" + "  }\r\n"
				+ "\r\n" + "  updatePagination();\r\n" + "  showPage(currentPage);\r\n" + "\r\n"
				+ "  function showPage(pageNumber) {\r\n" + "    var start = (pageNumber - 1) * rowsPerPage;\r\n"
				+ "    var end = start + rowsPerPage;\r\n" + "\r\n" + "    // Hide all rows\r\n"
				+ "    for (var i = 1; i < table.rows.length; i++) {\r\n"
				+ "      table.rows[i].style.display = \"none\";\r\n" + "    }\r\n" + "\r\n"
				+ "    // Display the rows for the current page\r\n"
				+ "    for (var i = start + 1; i < end + 1 && i < table.rows.length; i++) {\r\n"
				+ "      table.rows[i].style.display = \"\";\r\n" + "    }\r\n" + "  }\r\n" + "});\r\n"
				+ "</script>\r\n" + "\r\n" + "</body>\r\n" + "</html>");
		write(f, sb);
		return f;
	}

	public static void write(File f, StringBuilder sb) {
		// Write the HTML content to a file
		try {
			PrintWriter writer = new PrintWriter(f);
			writer.println(sb.toString());
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}