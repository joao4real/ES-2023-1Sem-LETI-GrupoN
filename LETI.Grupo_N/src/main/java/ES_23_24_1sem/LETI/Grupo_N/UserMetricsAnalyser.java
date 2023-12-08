package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JTextField resultadoField;
    private String[] fields;
    private String[] operators = {"--", "+", "-", "*", "/", "<", ">", ">=", "<=", "!=", "="};
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
        inputPanel = new JPanel(new GridLayout(1, 1)); // Use GridLayout for the input panel
        inputPanel.setPreferredSize(new Dimension(1500, 25)); // Set the initial size

        add(comboBoxPanel, BorderLayout.PAGE_START);
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton calcularButton = new JButton("Calcular");
        calcularButton.addActionListener(e -> calcular());
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            if (allowToCreate) {
                if (boxCounter > 0 && isEquationElement((String)boxes.get(boxes.size() - 1).getSelectedItem())) {
                    addJText();
                } else {
                    createNextComboBox();
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(calcularButton);
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
        if (boxes.isEmpty() || !boxes.get(boxes.size() - 1).getSelectedItem().equals("--")) {
            addComboBox();
        }
    }

    private void addJText() {
        resultadoField = new JTextField();
        resultadoField.setPreferredSize(COMPONENT_SIZE);

        // Create a panel to hold both JComboBox and JTextField
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

//        // Get the last JComboBox to set its size
//        JComboBox<String> lastComboBox = boxes.get(boxes.size() - 1);
//        lastComboBox.setMaximumSize(COMPONENT_SIZE);
//        panel.add(lastComboBox);
        panel.add(resultadoField);
        comboBoxPanel.add(panel);
        allowToCreate = false;
        expression = getExpression() + resultadoField.getText();


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

    private void calcular() {
        Object[] options = {"Yes, Evaluate", "Cancel"};
        int x = JOptionPane.showOptionDialog(this, "Is this the expression you want to run?\n" + expression,
                "Schedule Evaluator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                options[0]);
        // Add your calculation logic here if needed
    }

    private String getExpression() {
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