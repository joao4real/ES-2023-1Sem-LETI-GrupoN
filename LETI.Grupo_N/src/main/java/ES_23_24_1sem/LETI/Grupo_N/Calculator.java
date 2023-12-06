package ES_23_24_1sem.LETI.Grupo_N;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Calculator {

	private String type;
	private String[] data;
	private List<Boolean> list = new ArrayList<>();
	private HashMap<String, List<String>> sMap;
	private HashMap<String, List<String>> cMap;

	public Calculator(HashMap<String, List<String>> sMap, HashMap<String, List<String>> cMap, String s) {
		this.sMap = sMap;
		this.cMap = cMap;
		String[] data = s.split("  ");
		this.data = data;
		getType(data[0]);
	}

	private void getType(String d) {
		String x;
		x = null;
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
		case ("Inscritos no turno"):
			type = "i";
			break;
		case ("Hora início da aula"):
			type = "t";
			break;
		case ("Hora fim da aula"):
			type = "t";
			break;
		case ("Data da aula"):
			type = "d";
			break;
		default:
			type = "s";
			break;
		}
	}

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

	private void calculateDate() {
		for (int i = 0; i < sMap.get("Curso").size(); i++) {
			boolean b = sMap.get(data[0]).get(i).equals(sMap.get(data[2]).get(i));
			list.set(i, (data[1].equals("=")) ? b : !b);
		}
	}

	private void calculateTime() {
		for (int i = 0; i < sMap.get("Curso").size(); i++) {
			boolean b = sMap.get(data[0]).get(i).equals(sMap.get(data[2]).get(i));
			list.set(i, (data[1].equals("=")) ? b : !b);
		}
	}

	private void calculateString() {
		for (int i = 0; i < sMap.get("Curso").size(); i++) {
			String[] s1 = sMap.get(data[0]).get(i).split("/");
			String[] s2 = sMap.get(data[2]).get(i).split("/");
			boolean b = false;
			for (int j = 0; j < s1.length; j++)
				for (int k = 0; k < s2.length; k++)
					if (s1[j].equals(s2[k])) {
						b = true;
						break;
					}
			list.set(i, (data[1].equals("=")) ? b : !b);
		}
	}

	private void calculateInt() {
		int j = searchComparator(data);
		for (int i = 0; i < sMap.get("Curso").size(); i++) {
			int a = sumMetrics(Arrays.copyOfRange(data, 0, j));
			int b = Integer.parseInt(data[data.length - 1]);
			list.set(i, calculate(a, b, data[j]));
		}
	}

	private static int searchComparator(String[] data) {
		for (int i = 0; i < data.length; i++)
			if (data[i].equals(">") || data[i].equals("<") || data[i].equals(">=") || data[i].equals("<=")
					|| data[i].equals("!=") || data[i].equals("="))
				return i;
		return -1;
	}

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

	private static int sumMetrics(String[] arr) {
		int x = 0;
//		for (int i = 0; i < arr.length; i++)
//			switch (arr[i]) {
//			case ("+"):
//				x += arr[i];
//				break;
//			case ("-"):
//				x -= arr[i];
//				break;
//			case ("*"):
//				x *= arr[i];
//
//				break;
//			case ("/"):
//				x /= arr[i];
//
//				break;
//			default:
//				x = arr[i];
//			}
		return x;
	}
}
