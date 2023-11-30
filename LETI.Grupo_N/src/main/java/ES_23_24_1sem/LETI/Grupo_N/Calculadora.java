package ES_23_24_1sem.LETI.Grupo_N;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame {

    private JComboBox<String> comboBoxCampos;
    private JComboBox<String> comboBoxOperadores;
    private JComboBox<String> comboBoxSegundoCampo;
    private JComboBox<String> comboBoxTerceiroCampo;

    private JTextField resultadoField;

    private String[] campos = {"Inscritos no turno", "Características da sala pedida para a aula", "Características da sala atribuída",
            "Numero características", "Capacidade normal", "Capacidade exame"};

    private String[] operadores = {"+", "-", "*", "/", "<", ">", ">=", "<=", "!=", "="};

    private int tipoCampoSelecionado;

    public Calculadora() {
        setTitle("Calculadora de Campos");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        comboBoxCampos = new JComboBox<>(campos);
        comboBoxOperadores = new JComboBox<>(operadores);
        comboBoxSegundoCampo = new JComboBox<>();
        comboBoxTerceiroCampo = new JComboBox<>();
        resultadoField = new JTextField();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        add(comboBoxCampos);
        add(comboBoxOperadores);
        add(comboBoxSegundoCampo);
        add(comboBoxTerceiroCampo);
        add(resultadoField);

        comboBoxCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipoCampoSelecionado = comboBoxCampos.getSelectedIndex();
                atualizarComboBoxSegundoCampo();
                atualizarComboBoxTerceiroCampo();
            }
        });

        JButton calcularButton = new JButton("Calcular");
        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcular();
            }
        });
        add(calcularButton);

        setVisible(true);
    }

    private void atualizarComboBoxSegundoCampo() {
        comboBoxSegundoCampo.removeAllItems();

        for (String campo : campos) {
            if (tipoCampoSelecionado == 0 || (tipoCampoSelecionado != 0 && isCampoInt(campo))) {
                comboBoxSegundoCampo.addItem(campo);
            }
        }
    }

    private void atualizarComboBoxTerceiroCampo() {
        comboBoxTerceiroCampo.removeAllItems();

        for (String campo : campos) {
            if (tipoCampoSelecionado != 0 && isCampoInt(campo) && !campo.equals("Inscritos no turno")) {
                comboBoxTerceiroCampo.addItem(campo);
            } else if (tipoCampoSelecionado == 0 && isCampoInt(campo)) {
                comboBoxTerceiroCampo.addItem(campo);
            }
        }
    }


    private boolean isCampoInt(String campo) {
        return campo.equals("Inscritos no turno") ||
                campo.equals("Numero características") ||
                campo.equals("Capacidade normal") ||
                campo.equals("Capacidade exame");
    }

    private void calcular() {
        String campo1 = (String) comboBoxCampos.getSelectedItem();
        String operador = (String) comboBoxOperadores.getSelectedItem();
        String campo2 = (String) comboBoxSegundoCampo.getSelectedItem();
        String campo3 = (String) comboBoxTerceiroCampo.getSelectedItem();

        // Implemente a lógica para realizar as operações desejadas com os campos selecionados

        resultadoField.setText("Resultado da operação");
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

