package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculadora extends JFrame {

    private static final Dimension COMBO_BOX_SIZE = new Dimension(30, 25);
    private JPanel comboBoxPanel;  // Panel to hold combo boxes
    
    private List<JComboBox<String>> boxes;
    private JComboBox<String> comboBoxCampos;
    private JComboBox<String> comboBoxOperadores;
    private JTextField resultadoField;
    private List<String> operadoresStringArray = Arrays.asList("+", "-", "*", "/");
    private String[] campos = {"Inscritos no turno", "Características da sala pedida para a aula", "Características da sala atribuída",
            "Numero características", "Capacidade normal", "Capacidade exame"};

    private String[] operadoresGerais = {"+", "-", "*", "/", "<", ">", ">=", "<=", "!=", "="};
    private int boxCounter = 3;
    private int tipoCampoSelecionado;
    

    public Calculadora() {
    	boxes = new ArrayList<>();
        setTitle("Calculadora de Campos");
        setSize(800, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Create a panel for combo boxes and add it to the left
        comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Left-align combo boxes
        comboBoxCampos = createComboBox(campos,boxes);
        comboBoxOperadores = createComboBox(operadoresGerais,boxes);
        
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
    	JComboBox<String> newComboBox;
    	if(boxCounter % 2 == 0)
    		newComboBox = createComboBox(operadoresGerais, boxes);
    	else
    		newComboBox = createComboBox(campos, boxes);
    	boxCounter++;
        comboBoxPanel.add(newComboBox);
        atualizarComboBoxOperadores();
        revalidate();  // Update the layout
        repaint();
    }

    private void atualizarComboBoxOperadores() {
        comboBoxOperadores.removeAllItems();

        for (String operador : operadoresGerais) {
            if (!operadoresStringArray.contains(operador) || tipoCampoSelecionado != 1) {
                comboBoxOperadores.addItem(operador);
            }
        }
    }
    
	private void calcular() {
        String expression = "";
        Object[] options = {"Yes, Evaluate","Cancel"};
        for(JComboBox<String> box : boxes)
        	expression += box.getSelectedItem() +  " ";
        JOptionPane.showOptionDialog(this, "Is this the expression you want to run?\n" + expression,"Schedule Evaluator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    private static <T> JComboBox<T> createComboBox(T[] items, List<JComboBox<T>> boxes) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setMaximumSize(COMBO_BOX_SIZE);
        comboBox.setEditable(false);
        boxes.add(comboBox);
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
