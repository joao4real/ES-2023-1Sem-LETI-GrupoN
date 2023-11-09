package ES_23_24_1sem.LETI.Grupo_N;

import java.awt.Desktop;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
/**
 * Class to show a browser.
 */
public class ShowBrowser {
	 /**
     * Main method of the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("A Minha Aplicação");
        JButton button = new JButton("Mostrar Salas no Browser Web");
        button.setBounds(20, 20, 250, 50);

        button.addActionListener(new ActionListener() {
        	
        	  /**
             * Invoked when an action occurs.
             * @param e The event to be processed.
             */
            public void actionPerformed(ActionEvent e) {
                Desktop desk = Desktop.getDesktop();
                try {
                    // Use "file://" and specify a file:// URI to open the local file
                    File htmlFile = new File("target/classes/ES_23_24_1sem/LETI/Grupo_N/SalasDeAulaPorTiposDeSala.html");
                    desk.browse(htmlFile.toURI());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }
}