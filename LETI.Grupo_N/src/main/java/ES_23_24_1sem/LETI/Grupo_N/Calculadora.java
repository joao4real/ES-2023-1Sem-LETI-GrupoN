package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class Calculadora extends JFrame {

    private static final Dimension COMBO_BOX_SIZE = new Dimension(30, 25);
    private JPanel comboBoxPanel;  // Panel to hold combo boxes
    private JComboBox<String> comboBoxCampos;
    private JComboBox<String> comboBoxOperadores;
    private JTextField resultadoField;

    private String[] campos = {"Inscritos no turno", "Características da sala pedida para a aula", "Características da sala atribuída",
            "Numero características", "Capacidade normal", "Capacidade exame"};

    private String[] operadoresGerais = {"+", "-", "*", "/", "<", ">", ">=", "<=", "!=", "="};
    private List<String> operadoresStringArray = Arrays.asList("+", "-", "*", "/");

    private int tipoCampoSelecionado;

    public Calculadora() {
        setTitle("Calculadora de Campos");
        setSize(800, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Create a panel for combo boxes and add it to the left
        comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Left-align combo boxes
        comboBoxCampos = createComboBox(campos);
        comboBoxOperadores = createComboBox(operadoresGerais);
        comboBoxPanel.add(comboBoxCampos);
        comboBoxPanel.add(comboBoxOperadores);
        add(comboBoxPanel, BorderLayout.LINE_START); // Add combo box panel to the left

        // Add resultadoField to the center
        resultadoField = new JTextField();
        add(resultadoField, BorderLayout.CENTER);

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
                addComboBox();
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(calcularButton);
        add(buttonPanel, BorderLayout.LINE_END); // Add button panel to the right

        setVisible(true);
    }

    private void addComboBox() {
        // Create a new combo box and add it to the left of existing combo boxes
        JComboBox<String> newComboBox = createComboBox(operadoresGerais);
        comboBoxPanel.add(newComboBox);
        revalidate();  // Update the layout
        repaint();
    }

    private void calcular() {
        String campo1 = (String) comboBoxCampos.getSelectedItem();
        String operador = (String) comboBoxOperadores.getSelectedItem();

        // Implement logic for calculations

        resultadoField.setText("Resultado da operação");
    }

    private static <T> JComboBox<T> createComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setMaximumSize(COMBO_BOX_SIZE);
        comboBox.setEditable(false);
        return comboBox;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Calculadora();
            }
        });
    }
}
