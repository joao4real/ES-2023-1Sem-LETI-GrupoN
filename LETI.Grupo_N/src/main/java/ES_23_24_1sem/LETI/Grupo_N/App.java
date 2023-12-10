package ES_23_24_1sem.LETI.Grupo_N;

import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Main class of the application.
 */
public class App {

	private static JFrame frame;
	private static Schedule schedule;
	private static ClassroomsInfo database;
	private static String mapping = "X";
	private static String expression = "X";
	private static String[] timeConf = { "hh", "mm", "ss" };
    private static String[] dateConf = { "dd", "mm", "aaaa" };

	/**
     * The main method of the application.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException If an input/output exception occurs.
     */
	public static void main(String[] args) throws IOException {

		frame = new JFrame("Schedule Analyser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 250);

		database = ClassroomsInfo.createClassroomsInfoByRemoteFile(
				"https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/CaracterizacaoSalas.csv");

		JPanel cardPanel = new JPanel(new CardLayout());

		JPanel mainPanel = createMainPanel(cardPanel);
		JPanel importPanel = createImportPanel(cardPanel);
		JPanel optionsPanel = createOptionsPanel(cardPanel);

		cardPanel.add(mainPanel, "MAIN_PANEL");
		cardPanel.add(importPanel, "IMPORT_PANEL");
		cardPanel.add(optionsPanel, "OPTIONS_PANEL");

		CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
		cardLayout.show(cardPanel, "MAIN_PANEL");

		frame.add(cardPanel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
     * Creates the main panel of the application.
     *
     * @param cardPanel The card panel to switch between different panels.
     * @return The main panel.
     */
	static JPanel createMainPanel(JPanel cardPanel) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 0, 10, 0);

		panel.add(new JLabel("Do you have a pre-configurared text file?"), c);

		JButton yesButton = new JButton("Yes");
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(yesButton, c);

		JButton noButton = new JButton("No");
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_END;
		panel.add(noButton, c);

		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String input = JOptionPane.showInputDialog(frame, "Type the path for text file", null);
				if (input != null && !input.isEmpty()) {
					setUserConfiguration(new File(input));
					((CardLayout) cardPanel.getLayout()).show(cardPanel, "IMPORT_PANEL");
				}
			}
		});

		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "IMPORT_PANEL");
			}
		});

		return panel;
	}

	/**
     * Creates the import panel of the application.
     *
     * @param cardPanel The card panel to switch between different panels.
     * @return The import panel.
     */
	static JPanel createImportPanel(JPanel cardPanel) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 0, 10, 0);

		panel.add(new JLabel("Select which way you want to import schedule:"), c);

		JButton localButton = new JButton("Local");
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(localButton, c);

		JButton remoteButton = new JButton("Remote");
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_END;
		panel.add(remoteButton, c);

		localButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				schedule = getSchedule("l");
				if (getSchedule() != null)
					((CardLayout) cardPanel.getLayout()).show(cardPanel, "OPTIONS_PANEL");
			}
		});

		remoteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				schedule = getSchedule("r");
				if (getSchedule() != null)
					((CardLayout) cardPanel.getLayout()).show(cardPanel, "OPTIONS_PANEL");
			}
		});

		return panel;
	}

	/**
     * Creates the options panel of the application.
     *
     * @param cardPanel The card panel to switch between different panels.
     * @return The options panel.
     */
	static JPanel createOptionsPanel(JPanel cardPanel) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton backButton = new JButton("Back");
		JButton showScheduleButton = new JButton("Show Schedule");
		JButton evaluateScheduleButton = new JButton("Evaluate Schedule");

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 0, 10, 0);

		panel.add(new JLabel("Select what do you want to do with the schedule:"), c);

		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(showScheduleButton, c);

		c.gridy = 2;
		panel.add(evaluateScheduleButton, c);

		c.gridy = 3;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		panel.add(backButton, c);

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "IMPORT_PANEL");
			}
		});

		evaluateScheduleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(getExpression().equals("X"))
					new UserMetricsAnalyser(schedule.getMap(), database.getMap());
				else
					new UserMetricsAnalyser(schedule.getMap(), database.getMap(),getExpression());
			}
		});

		showScheduleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					openWebPage(HTMLFileCreator.createSchedule(getSchedule()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		return panel;
	}

	/**
     * Retrieves a Schedule object based on the specified option (local or remote).
     *
     * @param option The import option ("l" for local, "r" for remote).
     * @return The Schedule object.
     */
	static Schedule getSchedule(String option) {
		String input;

		switch (option) {
		case "l":
			input = JOptionPane.showInputDialog(frame, "Type the path for local file", null);
			if (input != null && !input.isEmpty()) {
				return Schedule.createScheduleByLocalFile(input, mapping);
			} else {
				return null;
			}
		case "r":
			input = JOptionPane.showInputDialog(frame, "Type the URL for remote file", null);
			if (input != null && !input.isEmpty()) {
				return Schedule.createScheduleByRemoteFile(input, mapping);
			} else {
				return null;
			}
		default:
			System.err.println("Invalid Option");
			return null;
		}
	}

	/**
     * Sets the user configuration based on the provided text file.
     *
     * @param textFile The text file containing user configuration.
     */
	public static void setUserConfiguration(File textFile) {

		try {
			Scanner sc = new Scanner(textFile);
			readTextFile(sc);
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
     * Reads the content of a text file and sets the mapping and expression.
     *
     * @param sc The scanner for reading the text file.
     */
	static void readTextFile(Scanner sc) {
        StringBuilder textMapping = new StringBuilder();
        int i = 0;
        String[] t;
        while (sc.hasNextLine()) {
            if (i < 11)
                textMapping.append(sc.nextLine() + "\n");
            else if (i == 11)
                expression = sc.nextLine();
            else {
                t = sc.nextLine().split("->");
                switch (t[0]) {
                case ("Date"):
                    dateConf = t[1].split("/");
                    break;
                default:
                    timeConf = t[1].split(":");
                }
            }
            i++;
        }
        mapping = textMapping.toString();
    }

	/**
     * Opens the default web browser to display the generated HTML schedule file.
     *
     * @param htmlFile The HTML file to be opened.
     */
	public static void openWebPage(File htmlFile) {
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
			Thread.sleep(5000);
			if (htmlFile.delete())
				System.out.println("HTML file deleted successfully!");
			else
				System.out.println("Failed to delete HTML file!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException x) {
			x.printStackTrace();
		}
	}

	/**
     * Retrieves the current schedule.
     *
     * @return The current Schedule object.
     */
	public static Schedule getSchedule() {
		return schedule;
	}

	/**
     * Retrieves the database information.
     *
     * @return The ClassroomsInfo object representing the database.
     */
	public static ClassroomsInfo getDatabase() {
		return database;
	}

	 /**
     * Retrieves the user expression for evaluating the schedule.
     *
     * @return The user expression.
     */
	public static String getExpression() {
		return expression;
	}
	
	 public static String[] getDateConf() {
	        return dateConf;
	    }

	    public static String[] getTimeConf() {
	        return timeConf;
	    }
}