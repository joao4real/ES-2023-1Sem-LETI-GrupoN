package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JPanel;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

public class AppTest {
	
	private PrintStream originalSystemOut;
    private ByteArrayOutputStream outputStream;
    private InputStream stdin;

	  /**
     * Tests the createHTMLFile method of the App class.
     * <p>
     * This test checks if the createHTMLFile method is able to create an HTML file from a Schedule object.
     * The Schedule object is created from a remote file. The test checks if the HTML file exists after its creation.
     * 
     * @throws IOException If an input/output exception occurs.
     */
    @Test
    public void testCreateHTMLFile() throws IOException {
    	Schedule schedule = Schedule.createScheduleByRemoteFile("https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/HorarioDeExemplo.csv", "Curso->Curso\nUnidade Curricular->Unidade Curricular\nTurno->Turno\nTurma->Turma\nInscritos no turno->Inscritos no turno\nDia da semana->Dia da semana\nHora início da aula->Hora início da aula\nHora fim da aula->Hora fim da aula\nData da aula->Data da aula\nCaracterísticas da sala pedida para a aula->Características da sala pedida para a aula\nSala atribuída à aula->Sala atribuída à aula");
        File htmlFile = HTMLFileCreator.createSchedule(schedule);
        assertNotNull(htmlFile);
        assertTrue(htmlFile.exists());
        assertTrue(htmlFile.isFile());
    }
    
    /**
     * Tests the openWebPage method of the App class.
     * <p>
     * This test checks if the openWebPage method does not throw an exception when given a valid HTML file.
     */
    @Test
    void testOpenWebPage() {
        File htmlFile = new File("Test_Open.html");
        assertDoesNotThrow(() -> App.openWebPage(htmlFile));
    }
    
    

    /**
     * Sets up the input stream before each test.
     */
    @BeforeEach
    void setUp() {
        stdin = System.in;
        originalSystemOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Tests the getSchedule method of the App class for a local file.
     * <p>
     * This test checks if the getSchedule method returns a non-null Schedule object when provided with a valid local file path.
     */
    @Test
    void testGetScheduleLocal() {
        provideInput("Testes_JUnit.csv");
        Schedule schedule = App.getSchedule("l");
        assertNotNull(schedule);
    }

    /**
     * Tests the getSchedule method of the App class for a remote file.
     * <p>
     * This test checks if the getSchedule method returns a non-null Schedule object when provided with a valid remote file URL.
     */
    @Test
    void testGetScheduleRemote() {
        provideInput("https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/HorarioDeExemplo.csv");
        Schedule schedule = App.getSchedule("r");
        assertNotNull(schedule);
    }

    /**
     * Tests the setUserConfiguration method of the App class.
     * <p>
     * This test checks if the setUserConfiguration method does not throw an exception when provided with valid input.
     *
     * @throws IOException If an input/output exception occurs.
     */
    @Test
    void testSetUserConfiguration() throws IOException {
        File textFile = new File("C:\\Users\\Pedro\\Desktop\\ScheduleConfigurator.txt");
        String userInput = "Aulas\nSalas";
        provideInput(userInput);
        App.setUserConfiguration(textFile);
    }

    /**
     * Tests the openWebPage method of the App class (variant for app).
     * <p>
     * This test checks if the openWebPage method does not throw an exception when given a valid HTML file (variant for app).
     */
    @Test
    void testOpenWebPage_app() {
        provideOutput();
        File htmlFile = new File("Test_Open.html");
        assertDoesNotThrow(() -> App.openWebPage(htmlFile));
    }
    
    /**
     * Tests the getDatabase method of the App class.
     * <p>
     * This test checks if the getDatabase method returns a non-null object.
     */
    @Test
    void testGetDatabase() {
        assertNotNull(App.getDatabase());
    }
    
    /**
     * Tests the createOptionsPanel method of the App class.
     * <p>
     * This test checks if the createOptionsPanel method returns a non-null JPanel object.
     */
    @Test
    void testCreateOptionsPanel() {
        JPanel panel = App.createOptionsPanel(new JPanel());
        assertNotNull(panel);
    }
    
    /**
     * Tests the main method of the App class.
     * <p>
     * This test checks if the main method does not throw an exception when provided with a valid input.
     */
    @Test
    void testMain() {
        provideInput("C:\\Users\\Pedro\\Desktop\\ScheduleConfigurator.txt");
        assertDoesNotThrow(() -> App.main(null));
        restoreInputAndOutput();
    }

    /**
     * Tests the createMainPanel method of the App class.
     * <p>
     * This test checks if the createMainPanel method returns a non-null JPanel object.
     */
    @Test
    void testCreateMainPanel() {
        JPanel panel = App.createMainPanel(new JPanel());
        assertNotNull(panel);
    }

    /**
     * Tests the createImportPanel method of the App class.
     * <p>
     * This test checks if the createImportPanel method returns a non-null JPanel object.
     */
    @Test
    void testCreateImportPanel() {
        JPanel panel = App.createImportPanel(new JPanel());
        assertNotNull(panel);
    }
    
    /**
     * Tests the readTextFile method of the App class.
     * <p>
     * This test checks if the readTextFile method reads input from a Scanner and sets the expression in the App class.
     */
    @Test
    void testReadTextFile() {
        Scanner mockedScanner = new Scanner("line1\nline2");
        App.readTextFile(mockedScanner);
        assertEquals("X", App.getExpression());
    }
    
    /**
     * Provides a way to suppress the output stream.
     */
    private void provideOutput() {
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            @Override
            public void write(int b) {
            }
        }));
    }
    
    /**
     * Provides input data to the System.in stream.
     *
     * @param data The data to be provided as input.
     */
    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }
    
    /**
     * Restores the original input and output streams.
     */
    private void restoreInputAndOutput() {
        System.setIn(stdin);
        System.setOut(originalSystemOut);
    }

    /**
     * Tears down the input stream after each test.
     */
    @AfterEach
    void tearDown() {
        System.setIn(stdin);
    }

    /**
     * Tests the readFile method of the App class.
     * <p>
     * This test checks if the readFile method reads input from a Scanner and populates a HashData object accordingly.
     */
    @Test
    public void testReadFile() {
        String fileData = "Nome;Idade;Cidade\nJoão;25;Porto\nPedro;30;Almada";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData.getBytes());
        Scanner mockScanner = new Scanner(inputStream);
        HashData hashData = new HashDataImpl();
        hashData.setLabels(new String[] { "Nome", "Idade", "Cidade" });
        hashData.readFile(mockScanner);
        assertEquals("João", hashData.getMap().get("Nome").get(0));
        assertEquals("25", hashData.getMap().get("Idade").get(0));
        assertEquals("Porto", hashData.getMap().get("Cidade").get(0));
        assertEquals("Pedro", hashData.getMap().get("Nome").get(1));
        assertEquals("30", hashData.getMap().get("Idade").get(1));
        assertEquals("Almada", hashData.getMap().get("Cidade").get(1));
    }

    /**
     * Tests the getMap method of the HashDataImpl class.
     * <p>
     * This test checks if the getMap method returns the expected results.
     */
    @Test
    public void testGetMap() {
        HashData hashData = new HashDataImpl();
        assertTrue(hashData.getMap().isEmpty());
        hashData.getMap().put("Key", new ArrayList<String>());
        hashData.getMap().get("Key").add("Salas");
        assertFalse(hashData.getMap().isEmpty());
        assertEquals("Salas", hashData.getMap().get("Key").get(0));
    }

    /**
     * Tests the setMap method of the HashDataImpl class.
     * <p>
     * This test checks if the setMap method sets the map in the HashDataImpl class as expected.
     */
    @Test
    public void testSetMap() {
        HashData hashData = new HashDataImpl();
        LinkedHashMap<String, List<String>> newMap = new LinkedHashMap<>();
        newMap.put("NewKey", new ArrayList<>());
        newMap.get("NewKey").add("NewValue");
        hashData.setMap(newMap);
        assertEquals(newMap, hashData.getMap());
    }

    /**
     * Tests the getMapSize method of the HashDataImpl class.
     * <p>
     * This test checks if the getMapSize method returns the expected size of the map in the HashDataImpl class.
     */
    @Test
    public void testGetMapSize() {
        HashData hashData = new HashDataImpl();
        hashData.setLabels(new String[] { "Nome", "Idade", "Cidade" });
        assertEquals(3, hashData.getMapSize());
    }

    /**
     * Tests the getLabels method of the HashDataImpl class.
     * <p>
     * This test checks if the getLabels method returns the expected labels in the HashDataImpl class.
     */
    @Test
    public void testGetLabels() {
        HashData hashData = new HashDataImpl();
        String[] labels = { "Nome", "Idade", "Cidade" };
        hashData.setLabels(labels);
        assertArrayEquals(labels, hashData.getLabels());
    }

    /**
     * Tests the setLabels method of the HashDataImpl class.
     * <p>
     * This test checks if the setLabels method sets the labels in the HashDataImpl class as expected.
     */
    @Test
    public void testSetLabels() {
        HashData hashData = new HashDataImpl();
        String[] labels = { "Nome", "Idade", "Cidade" };
        hashData.setLabels(labels);
        assertArrayEquals(labels, hashData.getLabels());
    }

    /**
     * A concrete implementation of the HashData abstract class for testing purposes.
     */
    private static class HashDataImpl extends HashData {}
}