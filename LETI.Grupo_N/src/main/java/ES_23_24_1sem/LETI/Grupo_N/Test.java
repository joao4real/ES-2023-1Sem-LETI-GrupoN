package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Test {

	private static JPanel panel;
	private static int comboBoxCount = 0;
	private static final Dimension COMBO_BOX_SIZE = new Dimension(100, 25);
	private static String expression = "";
	private static boolean endOfEquation = false;

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			
			JFrame frame = new JFrame("Schedule Analyser");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// Use BoxLayout with X_AXIS alignment
			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

			JButton addButton = new JButton("+");

			// Create an array of options
			String[] options = { "Option 1", "Option 2", "Option 3" };
			String[] symbols = { ">", "<", ">=", "<=", "=", "!=", "+", "-", "x", "/" };
			
			addButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<String> comboBox;
					if (comboBoxCount % 2 == 0) {
						
						comboBox = createComboBox(options);
						
					} else {
						
						if (!endOfEquation) {
							
							comboBox = createComboBox(symbols);
							
						} else {
							JTextField intField = new JTextField(5);
							int a = scanInteger(intField.getText());
						}
					}
				
					// Add the JComboBox to the original panel
					panel.add(comboBox);

					// Add ActionListener to update the expression variable
					comboBox.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent event) {
							if (isEquationElement((String) comboBox.getSelectedItem()))
								endOfEquation = true;
							expression += (String) comboBox.getSelectedItem() + " ";
							System.out.println(expression); // Print for testing
						}

					});

					comboBoxCount++;

					// Refresh the GUI
					panel.revalidate();
					panel.repaint();
				}
			});

			// Add components to the panel
			panel.add(addButton);
			frame.add(panel);
			frame.setSize(1000, 100);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

	private static <T> JComboBox<T> createComboBox(T[] items) {
		JComboBox<T> comboBox = new JComboBox<>(items);
		comboBox.setMaximumSize(COMBO_BOX_SIZE);
		return comboBox;
	}

	private static boolean isEquationElement(String s) {
		return (s.equals(">=") || s.equals("<=") || s.equals(">") || s.equals("<") || s.equals("=") || s.equals("!="))
				? true
				: false;
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
