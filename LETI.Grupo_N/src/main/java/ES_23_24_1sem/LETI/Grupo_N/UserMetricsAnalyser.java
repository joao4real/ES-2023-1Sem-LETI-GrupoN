package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class UserMetricsAnalyser extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Dimension COMPONENT_SIZE = new Dimension(100, 25);

	private JPanel comboBoxPanel;
	private JPanel inputPanel;
	private List<JComboBox<String>> boxes = new ArrayList<>();
	private LinkedHashMap<String, List<String>> sMap;
	private LinkedHashMap<String, List<String>> cMap;
	private JTextField fieldResult;
	private String[] fields;
	private String[] operators = { "--", "+", "-", "*", "/", "<", ">", ">=", "<=", "!=", "=" };
	private int boxCounter = 0;
	private String expression = "";
	private boolean allowToCreate = true;

	public UserMetricsAnalyser(LinkedHashMap<String, List<String>> sMap, LinkedHashMap<String, List<String>> cMap) {
		this.sMap = sMap;
		this.cMap = cMap;
		fields = getFields();
		setTitle("Schedule Evaluator");
		setSize(1500, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		inputPanel = new JPanel(new GridLayout(1, 1));
		inputPanel.setPreferredSize(new Dimension(1500, 25));

		add(comboBoxPanel, BorderLayout.PAGE_START);
		add(inputPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton calculateButton = new JButton("Calculate");
		calculateButton.addActionListener(e -> calculate());
		JButton addButton = new JButton("Add");
		addButton.addActionListener(e -> {
			if (allowToCreate) {
				if (boxCounter > 0 && isEquationElement((String) boxes.get(boxes.size() - 1).getSelectedItem()))
					addJText();
				else
					createNextComboBox();
			}
		});

		buttonPanel.add(addButton);
		buttonPanel.add(calculateButton);
		add(buttonPanel, BorderLayout.PAGE_END);

		setVisible(true);
	}

	private String[] getFields() {
		List<String> fieldList = new ArrayList<>();
		fieldList.add("--");
		fieldList.addAll(sMap.keySet());
		fieldList.addAll(cMap.keySet());
		return fieldList.toArray(new String[0]);
	}

	public void createNextComboBox() {
		if (boxes.isEmpty() || !boxes.get(boxes.size() - 1).getSelectedItem().equals("--"))
			addComboBox();
	}

	private void addJText() {
		fieldResult = new JTextField();
		fieldResult.setPreferredSize(COMPONENT_SIZE);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(fieldResult);
		comboBoxPanel.add(panel);
		allowToCreate = false;
		revalidate();
		repaint();
	}

	private void addComboBox() {
		JComboBox<String> newComboBox = createComboBox((boxCounter % 2 != 0) ? operators : fields);
		boxCounter++;
		comboBoxPanel.add(newComboBox);
		boxes.add(newComboBox);
		revalidate();
		repaint();
	}

	private void calculate() {
		Object[] options = { "Yes, Show Results", "Cancel" };
		expression += getSelectedItems() + fieldResult.getText();
		if (JOptionPane.showOptionDialog(this, "Is this the expression you want to run?\n" + expression,
				"Schedule Evaluator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]) == JOptionPane.YES_OPTION) {
			showSchedule();
		}
	}

	private void showSchedule() {
		App.openWebPage(HTMLFileCreator.createScheduleEvaluator(sMap,new Calculator(sMap, cMap,expression).calculate()));
		
		
	}

	private String getSelectedItems() {
		StringBuilder sb = new StringBuilder();
		for (JComboBox<String> box : boxes) {
			sb.append(box.getSelectedItem()).append(";");
		}
		return sb.toString();
	}

	private static <T> JComboBox<T> createComboBox(T[] items) {
		JComboBox<T> comboBox = new JComboBox<>(items);
		comboBox.setMaximumSize(COMPONENT_SIZE);
		comboBox.setEditable(false);
		return comboBox;
	}

	private static boolean isEquationElement(String s) {
		return (s.equals(">=") || s.equals("<=") || s.equals(">") || s.equals("<") || s.equals("=") || s.equals("!="));
	}
}