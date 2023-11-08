package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class testImport {

	  @Test
	    void testCreateScheduleByLocalFile() {
	        String path = "Testes_JUnit.csv";
	        Schedule schedule = Schedule.createScheduleByLocalFile(path);
	        assertFalse(schedule.getMap().isEmpty());
	        LinkedHashMap<String, List<String>> expectedContent = new LinkedHashMap<>();
	        expectedContent.put("nome", Arrays.asList("João", "Pedro", "Vicente"));
	        expectedContent.put("idade", Arrays.asList("20", "20", "19"));
	        expectedContent.put("transporte", Arrays.asList("Carro", "Comboio", "Trotinete"));

	        
	        assertEquals(expectedContent, schedule.getMap());
	    }

	    @Test
	    void testCreateScheduleByRemoteFile() {
	        String urlStr = "https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/LETI.Grupo_N/Testes_JUnit.csv";
	        Schedule schedule = Schedule.createScheduleByRemoteFile(urlStr);
	        assertFalse(schedule.getMap().isEmpty());
	        LinkedHashMap<String, List<String>> expectedContent = new LinkedHashMap<>();
	        expectedContent.put("nome", Arrays.asList("João", "Pedro", "Vicente"));
	        expectedContent.put("idade", Arrays.asList("20", "20", "19"));
	        expectedContent.put("transporte", Arrays.asList("Carro", "Comboio", "Trotinete"));
	        assertEquals(expectedContent, schedule.getMap());
	    }
}