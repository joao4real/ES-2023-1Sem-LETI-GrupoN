package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test_calculadora {
	

	 @Test
	    void testCalculateString() {
	        HashMap<String, List<String>> sMap = new HashMap<>();
	        sMap.put("Curso", Arrays.asList("Engenharia", "Informática", "Matemática"));
	        sMap.put("Aula", Arrays.asList("A1", "A2", "A3"));
	        sMap.put("Característica", Arrays.asList("Labs", "Teoria", "Projeto"));
	        HashMap<String, List<String>> cMap = new HashMap<>();
	        cMap.put("Nome sala", Arrays.asList("A1", "A2", "A3"));
	        Calculator calculator = new Calculator(sMap, cMap, "Característica;=;Labs");
	        List<Boolean> result = calculator.calculate();
	        assertEquals(Arrays.asList(true, false, false), result);
	    }

	 @Test
	    void testCalculateDate() {
	        HashMap<String, List<String>> sMap = new HashMap<>();
	        sMap.put("Curso", Arrays.asList("Engenharia", "Informática", "Matemática"));
	        sMap.put("Data da aula", Arrays.asList("2023-01-01", "2023-02-01", "2023-03-01"));
	        sMap.put("Hora início da aula", Arrays.asList("08:00:00", "09:00:00", "10:00:00"));
	        HashMap<String, List<String>> cMap = new HashMap<>();
	        cMap.put("Nome sala", Arrays.asList("A1", "A2", "A3"));
	        Calculator calculator = new Calculator(sMap, cMap, "Hora início da aula;=;09:00:00");
	        List<Boolean> result = calculator.calculate();
	        assertEquals(Arrays.asList(false, true, false), result); 
	    }

	    @Test
	    void testCalculateTime() {
	        HashMap<String, List<String>> sMap = new HashMap<>();
	        sMap.put("Curso", Arrays.asList("Engenharia", "Informática", "Matemática"));
	        sMap.put("Hora início da aula", Arrays.asList("08:00:00", "09:00:00", "10:00:00"));
	        HashMap<String, List<String>> cMap = new HashMap<>();
	        cMap.put("Nome sala", Arrays.asList("A1", "A2", "A3"));
	        Calculator calculator = new Calculator(sMap, cMap, "Hora início da aula;=;09:00:00");
	        List<Boolean> result = calculator.calculate();
	        assertEquals(Arrays.asList(false, true, false), result);
	    }


	    @Test
	    void testCalculateInt() {
	        HashMap<String, List<String>> sMap = new HashMap<>();
	        sMap.put("Curso", Arrays.asList("Engenharia", "Informática", "Matemática"));
	        sMap.put("Inscritos no turno", Arrays.asList("20", "15", "25"));
	        sMap.put("Sala atribuída à aula", Arrays.asList("A1", "A2", "A3"));
	        HashMap<String, List<String>> cMap = new HashMap<>();
	        cMap.put("Nome sala", Arrays.asList("A1", "A2", "A3"));
	        cMap.put("Inscritos no turno", Arrays.asList("20", "15", "25"));
	        Calculator calculator = new Calculator(sMap, cMap, "Inscritos no turno;>;10");
	        List<Boolean> result = calculator.calculate();
	        assertEquals(Arrays.asList(true, true, true), result);    
	    }
}