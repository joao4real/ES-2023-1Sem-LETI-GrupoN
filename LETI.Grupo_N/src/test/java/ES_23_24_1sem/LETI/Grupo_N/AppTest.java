package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.File;

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
    	Schedule schedule = Schedule.createScheduleByRemoteFile("https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/HorarioDeExemplo.csv");
        File htmlFile = App.createHTMLFile(schedule);
        assertNotNull(htmlFile);
        assertTrue(htmlFile.exists());
        assertTrue(htmlFile.isFile());
    }
    
    @Test
    void testOpenWebPage() {
        File htmlFile = new File("Test_Open.html");
        assertDoesNotThrow(() -> App.openWebPage(htmlFile));
    }

    @Test
    void testIsClassWithoutRoom() {
        assertTrue(App.isClassWithoutRoom(createScheduleMapWithoutRoom(), 0));
        assertFalse(App.isClassWithoutRoom(createScheduleMapWithRoom(), 0));
    }
    
    @Test
    void testNoRoomRequested() {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        map.put("Características da sala pedida para a aula", Arrays.asList("Não necessita de sala"));
        assertNull(App.getRoomRequirements(map, 0));
    }

    @Test
    void testRoomRequested() {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        map.put("Características da sala pedida para a aula", Arrays.asList("Grande/Auditório"));
        List<String> expectedRequirements = Arrays.asList("Grande", "Auditório");
        assertEquals(expectedRequirements, App.getRoomRequirements(map, 0));
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
    
    private LinkedHashMap<String, List<String>> createScheduleMapWithoutRoom() {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        map.put("Sala atribuída à aula", Arrays.asList("N/A", "N/A"));
        map.put("Características da sala pedida para a aula", Arrays.asList("Exame", "laboratório"));
        return map;
    }

    private LinkedHashMap<String, List<String>> createScheduleMapWithRoom() {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        map.put("Sala atribuída à aula", Arrays.asList("Sala1", "Sala2"));
        map.put("Características da sala pedida para a aula", Arrays.asList("Exame", "laboratório"));
        return map;
    }
}