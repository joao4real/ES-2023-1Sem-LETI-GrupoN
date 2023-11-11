package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class testImport {

    /**
     * Tests the createScheduleByLocalFile method of the Schedule class.
     * <p>
     * This test checks if the createScheduleByLocalFile method is able to create a Schedule object from a local file.
     * The test checks if the Schedule object is not null and if its content matches the expected content.
     */
	  @Test
	    void testCreateScheduleByLocalFile() {
	        String path = "Testes_JUnit.csv";
	        Schedule schedule = Schedule.createScheduleByLocalFile(path);
	        assertFalse(schedule.getMap().isEmpty());
	        LinkedHashMap<String, List<String>> expectedContent = new LinkedHashMap<>();
	        expectedContent.put("nome", Arrays.asList("João", "Pedro", "Vicente"));
	        expectedContent.put("idade", Arrays.asList("20", "20", "19"));
	        expectedContent.put("transporte", Arrays.asList("Carro", "Comboio", "Trotinete"));
	        assertNotNull(schedule);
	        assertEquals(expectedContent, schedule.getMap());
	    }

	    /**
	     * Tests the createScheduleByRemoteFile method of the Schedule class.
	     * <p>
	     * This test checks if the createScheduleByRemoteFile method is able to create a Schedule object from a remote file.
	     * The test checks if the Schedule object is not null and if its content matches the expected content.
	     */
	    @Test
	    void testCreateScheduleByRemoteFile() {
	        String urlStr = "https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/LETI.Grupo_N/Testes_JUnit.csv";
	        Schedule schedule = Schedule.createScheduleByRemoteFile(urlStr);
	        assertFalse(schedule.getMap().isEmpty());
	        LinkedHashMap<String, List<String>> expectedContent = new LinkedHashMap<>();
	        expectedContent.put("nome", Arrays.asList("João", "Pedro", "Vicente"));
	        expectedContent.put("idade", Arrays.asList("20", "20", "19"));
	        expectedContent.put("transporte", Arrays.asList("Carro", "Comboio", "Trotinete"));
	        assertNotNull(schedule);
	        assertEquals(expectedContent, schedule.getMap());
	    }
}