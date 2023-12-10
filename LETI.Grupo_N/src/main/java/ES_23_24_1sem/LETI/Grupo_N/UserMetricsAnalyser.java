package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The UserMetricsAnalyser class provides a GUI for creating and evaluating expressions related to schedule metrics.
 */
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

	/**
     * Constructs a UserMetricsAnalyser object with schedule and course maps.
     *
     * @param sMap The schedule map.
     * @param cMap The course map.
     */
	public UserMetricsAnalyser(LinkedHashMap<String, List<String>> sMap, LinkedHashMap<String, List<String>> cMap) {
		this.sMap = sMap;
		this.cMap = cMap;
		fields = getFields();
		initialize();
	}

	/**
     * Constructs a UserMetricsAnalyser object with schedule and course maps and a predefined expression.
     *
     * @param sMap       The schedule map.
     * @param cMap       The course map.
     * @param expression The predefined expression.
     */
	public UserMetricsAnalyser(LinkedHashMap<String, List<String>> sMap, LinkedHashMap<String, List<String>> cMap,
			String expression) {
		this.sMap = sMap;
		this.cMap = cMap;
		this.expression = expression;
		showSchedule();
	}

	/**
     * Gets the list of fields for the schedule and course maps.
     *
     * @return An array of field names.
     */
	String[] getFields() {
		List<String> fieldList = new ArrayList<>();
		fieldList.add("--");
		fieldList.addAll(sMap.keySet());
		fieldList.addAll(cMap.keySet());
		return fieldList.toArray(new String[0]);
	}

	 /**
     * Initializes the UserMetricsAnalyser GUI.
     */
	private void initialize() {
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

	/**
     * Creates the next combo box based on user input.
     */
	public void createNextComboBox() {
		if (boxes.isEmpty() || !boxes.get(boxes.size() - 1).getSelectedItem().equals("--"))
			addComboBox();
	}

	/**
     * Adds a JTextfield for entering a result.
     */
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

	/**
     * Adds a combo box to the GUI.
     */
	private void addComboBox() {
		JComboBox<String> newComboBox = createComboBox((boxCounter % 2 != 0) ? operators : fields);
		boxCounter++;
		comboBoxPanel.add(newComboBox);
		boxes.add(newComboBox);
		revalidate();
		repaint();
	}

	/**
     * Calculates the expression and shows the schedule.
     */
	void calculate() {
		Object[] options = { "Yes, Show Results", "Cancel" };
		expression += getSelectedItems() + fieldResult.getText();
		if (JOptionPane.showOptionDialog(this, "Is this the expression you want to run?\n" + expression.replace(";"," "),
				"Schedule Evaluator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]) == JOptionPane.YES_OPTION) {
			write(expression, new File(System.getProperty("user.home") + "\\Desktop\\ScheduleConfigurator.txt"));
			showSchedule();
		}
	}

	/**
     * Writes the expression to a file.
     *
     * @param expression The expression to write.
     * @param file       The file to write to.
     */
	private void write(String expression, File file) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
			writer.write(expression);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
     * Shows the schedule based on the calculated expression.
     */
	private void showSchedule() {
		App.openWebPage(
				HTMLFileCreator.createScheduleEvaluator(sMap, new Calculator(sMap, cMap, expression).calculate()));
		System.exit(0);
	}

	/**
     * Gets the selected items from the combo boxes.
     *
     * @return A string containing the selected items.
     */
	public String getSelectedItems() {
		StringBuilder sb = new StringBuilder();
		for (JComboBox<String> box : boxes) {
			sb.append(box.getSelectedItem()).append(";");
		}
		return sb.toString();
	}

	/**
     * Creates a JComboBox with the specified items.
     *
     * @param <T>   The type of items.
     * @param items The items for the combo box.
     * @return A JComboBox.
     */
	private static <T> JComboBox<T> createComboBox(T[] items) {
		JComboBox<T> comboBox = new JComboBox<>(items);
		comboBox.setMaximumSize(COMPONENT_SIZE);
		comboBox.setEditable(false);
		return comboBox;
	}

	/**
     * Checks if the provided string is an operator in an equation.
     *
     * @param s The string to check.
     * @return true if the string is an operator, false otherwise.
     */
	private static boolean isEquationElement(String s) {
		return (s.equals(">=") || s.equals("<=") || s.equals(">") || s.equals("<") || s.equals("=") || s.equals("!="));
	}
}