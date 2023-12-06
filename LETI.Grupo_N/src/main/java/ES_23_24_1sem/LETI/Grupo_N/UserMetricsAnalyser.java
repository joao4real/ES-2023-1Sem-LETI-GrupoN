package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserMetricsAnalyser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Dimension COMBO_BOX_SIZE = new Dimension(30, 25);
	private JPanel comboBoxPanel; // Panel to hold combo boxes

	private List<JComboBox<String>> boxes;
	private JComboBox<String> comboBoxCampos;
	private JComboBox<String> comboBoxOperadores;
	private JTextField resultadoField;
	private String[] fields;
	private String[] operadoresGerais = { "+", "-", "*", "/", "<", ">", ">=", "<=", "!=", "=" };
	private int boxCounter = 3;
	private String expression;

	private boolean allowToCreate = true;

	public UserMetricsAnalyser(String[] scheduleFields, String[] databaseFields) {

		fields = getFields(scheduleFields, databaseFields);
		boxes = new ArrayList<>();
		setTitle("Calculadora de Campos");
		setSize(800, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		// Create a panel for combo boxes and add it to the left
		comboBoxPanel = new JPanel();
		comboBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Left-align combo boxes
		comboBoxCampos = createComboBox(fields, boxes);
		comboBoxOperadores = createComboBox(operadoresGerais, boxes);

		comboBoxPanel.add(comboBoxCampos);
		comboBoxPanel.add(comboBoxOperadores);
		add(comboBoxPanel, BorderLayout.LINE_START); // Add combo box panel to the left

		// Create a panel for buttons and add it to the right
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Right-align buttons
		JButton calcularButton = new JButton("Calcular");
		calcularButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcular();
			}
		});
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (allowToCreate)
					addComboBox();
				else {
					expression += scanInteger(resultadoField.getText());
					resultadoField = new JTextField();
					add(resultadoField, BorderLayout.CENTER);
				}
			}
		});
		buttonPanel.add(addButton);
		buttonPanel.add(calcularButton);
		add(buttonPanel, BorderLayout.LINE_END); // Add button panel to the right
		setVisible(true);
	}

	private String[] getFields(String[] scheduleFields, String[] databaseFields) {
		String[] fields = new String[scheduleFields.length + databaseFields.length];

		for (int i = 0; i < scheduleFields.length; i++)
			fields[i] = scheduleFields[i];

		for (int j = scheduleFields.length; j < fields.length; j++)
			fields[j] = databaseFields[j - scheduleFields.length];

		return fields;
	}

	private void addComboBox() {
		JComboBox<String> newComboBox;
		if (boxCounter % 2 == 0)
			newComboBox = createComboBox(operadoresGerais, boxes);
		else
			newComboBox = createComboBox(fields, boxes);
		boxCounter++;
		comboBoxPanel.add(newComboBox);
//        atualizarComboBoxOperadores();
		revalidate(); // Update the layout
		repaint();
	}

//  private void atualizarComboBoxOperadores() {
//        comboBoxOperadores.removeAllItems();
//
//        for (String operador : operadoresGerais) {
//            if (!operadoresStringArray.contains(operador) || tipoCampoSelecionado != 1) {
//                comboBoxOperadores.addItem(operador);
//            }
//        }
//    }

	private void calcular() {
		Object[] options = { "Yes, Evaluate", "Cancel" };
		JOptionPane.showOptionDialog(this, "Is this the expression you want to run?\n" + getExpression(),
				"Schedule Evaluator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]);
		System.out.println(getExpression());
	}

	private String getExpression() {
		expression = "";
		String item = "";
		for (JComboBox<String> box : boxes) {
			item = (String) box.getSelectedItem();
			expression += item + "  ";
			if (isEquationElement(item))
				System.out.println("PASSEI POR AQUI");
				allowToCreate = false;
		}
		return expression;
	}

	private static <T> JComboBox<T> createComboBox(T[] items, List<JComboBox<T>> boxes) {
		JComboBox<T> comboBox = new JComboBox<>(items);
		comboBox.setMaximumSize(COMBO_BOX_SIZE);
		comboBox.setEditable(false);
		boxes.add(comboBox);
		return comboBox;
	}

	private static boolean isEquationElement(String s) {
		return (s.equals(">=") || s.equals("<=") || s.equals(">") || s.equals("<") || s.equals("=") || s.equals("!="));
	}

	private static int scanInteger(String input) {
		int a;
		try {
			a = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.err.println("Invalid input. Please enter an integer.");
			return -1;
		}
		return a;
	}
}
