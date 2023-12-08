package ES_23_24_1sem.LETI.Grupo_N;

import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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

	public static void main(String[] args) throws IOException {

		frame = new JFrame("Schedule Analyser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 250);
		
		database = ClassroomsInfo.createClassroomsInfoByRemoteFile("https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/CaracterizacaoSalas.csv");

		JPanel cardPanel = new JPanel(new CardLayout());

		JPanel importPanel = createImportPanel(cardPanel);
		JPanel optionsPanel = createOptionsPanel(cardPanel);

		cardPanel.add(importPanel, "IMPORT_PANEL");
		cardPanel.add(optionsPanel, "OPTIONS_PANEL");

		CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
		cardLayout.show(cardPanel, "IMPORT_PANEL");

		frame.add(cardPanel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static JPanel createImportPanel(JPanel cardPanel) {
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

	private static JPanel createOptionsPanel(JPanel cardPanel) {
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
				new UserMetricsAnalyser(schedule.getMap(), database.getMap());
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

	private static Schedule getSchedule(String option) {
		String input;

		switch (option) {
		case "l":
			input = JOptionPane.showInputDialog(frame, "Type the path for local file", null);
			if (input != null && !input.isEmpty()) {
				return Schedule.createScheduleByLocalFile(input);
			} else {
				return null;
			}
		case "r":
			input = JOptionPane.showInputDialog(frame, "Type the URL for remote file", null);
			if (input != null && !input.isEmpty()) {
				return Schedule.createScheduleByRemoteFile(input);
			} else {
				return null;
			}
		default:
			System.err.println("Invalid Option");
			return null;
		}
	}

	public static void openWebPage(File htmlFile) {
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
			Thread.sleep(500);
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

	public static Schedule getSchedule() {
		return schedule;
	}

	public static ClassroomsInfo getDatabase() {
		return database;
	}
}