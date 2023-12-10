package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;

public class AppTest {

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
    
    @Test
    void testOpenWebPage() {
        File htmlFile = new File("Test_Open.html");
        assertDoesNotThrow(() -> App.openWebPage(htmlFile));
    }
    private InputStream stdin;

    @BeforeEach
    void setUp() {
        stdin = System.in;
    }

    @Test
    void testGetScheduleLocal() {
        provideInput("Testes_JUnit.csv");
        Schedule schedule = App.getSchedule("l");
        assertNotNull(schedule);
    }

    @Test
    void testGetScheduleRemote() {
        provideInput("https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/HorarioDeExemplo.csv");
        Schedule schedule = App.getSchedule("r");
        assertNotNull(schedule);
    }

    @Test
    void testSetUserConfiguration() throws IOException {
        File textFile = new File("C:\\Users\\Pedro\\Desktop\\ScheduleConfigurator.txt");
        String userInput = "line1\nline2";
        provideInput(userInput);
        App.setUserConfiguration(textFile);
    }

    @Test
    void testOpenWebPage_app() {
        provideOutput();
        File htmlFile = new File("Test_Open.html");
        assertDoesNotThrow(() -> App.openWebPage(htmlFile));
    }
    
    @Test
    void testGetDatabase() {
        assertNull(App.getDatabase());
    }
    
    private void provideOutput() {
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            @Override
            public void write(int b) {
            }
        }));
    }
    
    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @AfterEach
    void tearDown() {
        System.setIn(stdin);
    }

    /*
    @Test
    void testAnalyse() {
        Schedule schedule = Schedule.createScheduleByRemoteFile("https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/HorarioDeExemplo.csv");
        ClassroomsInfo classroomsInfo = ClassroomsInfo.createClassroomsInfoByRemoteFile("https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/CaracterizacaoSalas.csv");
        List<Integer> overCapacity = new ArrayList<>();
        List<Boolean> matchRequirements = new ArrayList<>();
        List<Integer> featuresNotUsed = new ArrayList<>();
        List<Boolean> classWithoutRoom = new ArrayList<>();
        App.analyse(schedule, classroomsInfo, overCapacity, matchRequirements, featuresNotUsed, classWithoutRoom);

        // Adicionar após poder analisar ficheiro
    }
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

    @Test
    public void testGetMap() {
        HashData hashData = new HashDataImpl();
        assertTrue(hashData.getMap().isEmpty());
        hashData.getMap().put("Key", new ArrayList<String>());
        hashData.getMap().get("Key").add("Salas");
        assertFalse(hashData.getMap().isEmpty());
        assertEquals("Salas", hashData.getMap().get("Key").get(0));
    }

    @Test
    public void testSetMap() {
        HashData hashData = new HashDataImpl();
        LinkedHashMap<String, List<String>> newMap = new LinkedHashMap<>();
        newMap.put("NewKey", new ArrayList<>());
        newMap.get("NewKey").add("NewValue");
        hashData.setMap(newMap);
        assertEquals(newMap, hashData.getMap());
    }

    @Test
    public void testGetMapSize() {
        HashData hashData = new HashDataImpl();
        hashData.setLabels(new String[] { "Nome", "Idade", "Cidade" });
        assertEquals(3, hashData.getMapSize());
    }

    @Test
    public void testGetLabels() {
        HashData hashData = new HashDataImpl();
        String[] labels = { "Nome", "Idade", "Cidade" };
        hashData.setLabels(labels);
        assertArrayEquals(labels, hashData.getLabels());
    }

    @Test
    public void testSetLabels() {
        HashData hashData = new HashDataImpl();
        String[] labels = { "Nome", "Idade", "Cidade" };
        hashData.setLabels(labels);
        assertArrayEquals(labels, hashData.getLabels());
    }

 
    private static class HashDataImpl extends HashData {}
}