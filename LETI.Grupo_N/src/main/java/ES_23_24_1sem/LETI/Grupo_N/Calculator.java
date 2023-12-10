package ES_23_24_1sem.LETI.Grupo_N;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Calculator class performs calculations based on specified criteria for a given set of data.
 */
public class Calculator {

	private String type;
	private String[] data;
	List<Boolean> list = new ArrayList<>();
	private HashMap<String, List<String>> sMap;
	private HashMap<String, List<String>> cMap;

	/**
     * Constructs a Calculator object with the provided data maps and calculation expression.
     *
     * @param sMap The source data map.
     * @param cMap The comparison data map.
     * @param s    The calculation expression.
     */
	public Calculator(HashMap<String, List<String>> sMap, HashMap<String, List<String>> cMap, String s) {
		this.sMap = sMap;
		this.cMap = cMap;
		String[] data = s.split(";");
		this.data = data;
		getType(data[0]);
	}

	private void getType(String d) {
		String x = null;
		for (String s : sMap.keySet())
			if (s.equals(d))
				x = d;
		if (x != null)
			for (String s : cMap.keySet())
				if (s.equals(d))
					x = d;
		fillType(x);
	}

	private void fillType(String x) {
		switch (x) {
		case "Inscritos no turno":
		case "Capacidade Normal":
		case "Capacidade Exame":
		case "Número Características":
			type = "i";
			break;
		case "Hora início da aula":
		case "Hora fim da aula":
			type = "t";
			break;
		case "Data da aula":
			type = "d";
			break;
		default:
			type = "s";
			break;
		}
	}

	/**
     * Performs calculations based on the specified type and updates the result list.
     *
     * @return The list of Boolean results after calculations.
     */
	public List<Boolean> calculate() {
		switch (type) {
		case ("s"):
			calculateString();
			break;
		case ("t"):
			calculateTime();
			break;
		case ("d"):
			calculateDate();
			break;
		case ("i"):
			calculateInt();
			break;
		}
		return list;
	}

	/**
     * Performs date-based calculations and updates the result list.
     */
	void calculateDate() {
		for (int i = 0; i < sMap.get("Curso").size(); i++) {
			Date d = new Date(sMap.get(data[2]) != null ? sMap.get(data[2]).get(i) : data[2]);
			list.add(sMap.get(data[0]).get(i) == "N/A" ? false
					: calculate(new Date(sMap.get(data[0]).get(i)).compareTo(d), 0, data[1]));
		}
	}

	/**
     * Performs time-based calculations and updates the result list.
     */
	void calculateTime() {
		for (int i = 0; i < sMap.get("Curso").size(); i++) {
			Time d = new Time(sMap.get(data[2]) != null ? sMap.get(data[2]).get(i) : data[2]);
			list.add(sMap.get(data[0]).get(i) == "N/A" ? false
					: calculate(new Time(sMap.get(data[0]).get(i)).compareTo(d), 0, data[1]));
		}
	}

	/**
     * Performs string-based calculations and updates the result list.
     */
	void calculateString() {
		for (int i = 0; i < sMap.get("Curso").size(); i++) {
			String[] s1 = smartSplit(sMap.get(data[0]).get(i));
			String[] s2 = sMap.get(data[2]) != null ? smartSplit(sMap.get(data[2]).get(i)) : new String[] { data[2] };
			boolean b = false;
			for (int j = 0; j < s1.length; j++)
				for (int k = 0; k < s2.length; k++)
					if (s1[j].equals(s2[k])) {
						b = true;
						break;
					}
			list.add(data[1].equals("=") ? b : !b);
		}
	}

	/**
     * Splits a string in a smart way, considering both commas and slashes as separators.
     *
     * @param s The input string to be split.
     * @return An array of strings after splitting.
     */
	private String[] smartSplit(String s) {
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == ',')
				return s.split(",");
		return s.split("/");
	}

	/**
     * Performs integer-based calculations and updates the result list.
     */
	void calculateInt() {
		for (int i = 0; i < sMap.get("Curso").size(); i++) {
			int a = intSolver(i);
			int j = cMap.get("Nome sala").indexOf(sMap.get("Sala atribuída à aula").get(i));
			list.add(j == -1 ? false
					: calculate(a,
							Integer.parseInt(isKey(data[data.length - 1]) ? cMap.get(data[data.length - 1]).get(j)
									: data[data.length - 1]),
							data[data.length - 2]));
		}
	}

	private boolean isKey(String s) {
		return sMap.get(s) != null || cMap.get(s) != null;
	}

	/**
     * Performs the actual calculation based on the provided values and operator.
     *
     * @param a The first operand.
     * @param b The second operand.
     * @param s The operator.
     * @return The result of the calculation.
     */
	private static boolean calculate(int a, int b, String s) {
		switch (s) {
		case ("<"):
			return a < b;
		case (">"):
			return a > b;
		case ("<="):
			return a <= b;
		case (">="):
			return a >= b;
		case ("!="):
			return a != b;
		default:
			return a == b;
		}
	}

	private int intSolver(int i) {
		int x = 0;
		for (int j = 0; j < data.length - 2; j++)
			switch (data[j]) {
			case ("+"):
				x += getInt(++j, i);
				break;
			case ("-"):
				x -= getInt(++j, i);
				break;
			case ("*"):
				x *= getInt(++j, i);
				break;
			case ("/"):
				x /= getInt(++j, i);
				break;
			default:
				x = getInt(j, i);
			}
		return x;
	}

	private int getInt(int j, int i) {
			return Integer.parseInt(isKey(data[j]) ? sMap.get(data[j]) != null ? sMap.get(data[j]).get(i) : cMap.get(data[j]).get(i) : data[j]);
	}
}