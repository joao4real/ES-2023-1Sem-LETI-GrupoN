package ES_23_24_1sem.LETI.Grupo_N;

//import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import org.junit.Test;

public class Test_calculadora {
	
//    @Test
//    public void testCalculateStringEquality() {
//    	LinkedHashMap<String, List<String>> sMap = createSampleSMap();
//    	LinkedHashMap<String, List<String>> cMap = createSampleCMap();
//        Calculator calculator = new Calculator(sMap, cMap, "Inscritos no turno;=;Inscritos na sala");
//        List<Boolean> result = calculator.calculate();
//
//        assertTrue(result.get(0));
//        assertFalse(result.get(1));
//    }
//
//    @Test
//    public void testCalculateStringInequality() {
//    	LinkedHashMap<String, List<String>> sMap = createSampleSMap();
//    	LinkedHashMap<String, List<String>> cMap = createSampleCMap();
//        Calculator calculator = new Calculator(sMap, cMap, "Inscritos no turno;!=;Capacidade Exame");
//        System.out.println( "!");
//        List<Boolean> result = calculator.calculate();
//
//        assertFalse(result.get(0));
//        assertTrue(result.get(1));
//    }
//
//    @Test
//    public void testCalculateTimeComparison() {
//    	LinkedHashMap<String, List<String>> sMap = createSampleSMap();
//    	LinkedHashMap<String, List<String>> cMap = createSampleCMap();
//        Calculator calculator = new Calculator(sMap, cMap, "Hora início da aula;>;Hora fim da aula");
//        List<Boolean> result = calculator.calculate();
//
//        assertFalse(result.get(0));
//        assertTrue(result.get(1));
//    }
//
//    @Test
//    public void testCalculateDateComparison() {
//    	LinkedHashMap<String, List<String>> sMap = createSampleSMap();
//    	LinkedHashMap<String, List<String>> cMap = createSampleCMap();
//        Calculator calculator = new Calculator(sMap, cMap, "Data da aula;<=;Data do exame");
//        List<Boolean> result = calculator.calculate();
//
//        assertTrue(result.get(0));
//        assertFalse(result.get(1));
//    }
//
//    @Test
//    public void testCalculateIntExpression() {
//    	LinkedHashMap<String, List<String>> sMap = createSampleSMap();
//    	LinkedHashMap<String, List<String>> cMap = createSampleCMap();
//        Calculator calculator = new Calculator(sMap, cMap, "Número Características;<=;2+3*4-1");
//        List<Boolean> result = calculator.calculate();
//
//        assertTrue(result.get(0));
//        assertFalse(result.get(1));
//    }
//
//    @Test
//    public void testCalculateIntWithMapValues() {
//        LinkedHashMap<String, List<String>> sMap = createSampleSMap();
//        LinkedHashMap<String, List<String>> cMap = createSampleCMap();
//        Calculator calculator = new Calculator(sMap, cMap, "Número Características;>;Capacidade Normal+3");
//        List<Boolean> result = calculator.calculate();
//
//        assertTrue(result.get(0));
//        assertFalse(result.get(1));
//    }
//    
//    private LinkedHashMap<String, List<String>> createSampleSMap() {
//        LinkedHashMap<String, List<String>> sMap = new LinkedHashMap<>();
//        sMap.put("Inscritos no turno", Arrays.asList("50", "N/A"));
//        sMap.put("Capacidade Exame", Arrays.asList("50", "N/A"));
//        sMap.put("Hora início da aula", Arrays.asList("10:00", "12:00"));
//        sMap.put("Hora fim da aula", Arrays.asList("11:00", "14:00"));
//        sMap.put("Data da aula", Arrays.asList("2023-01-01", "2023-01-10"));
//        return sMap;
//    }
//
//    private LinkedHashMap<String, List<String>> createSampleCMap() {
//        LinkedHashMap<String, List<String>> cMap = new LinkedHashMap<>();
//        cMap.put("Nome sala", Arrays.asList("Sala A", "Sala B"));
//        cMap.put("Capacidade Normal", Arrays.asList("50", "30"));
//        cMap.put("Capacidade Exame", Arrays.asList("70", "40"));
//        cMap.put("Número Características", Arrays.asList("2", "3"));
//        return cMap;
//    }
//
//    @Test
//    public void testCalculate() {
//        HashMap<String, List<String>> sMap = new HashMap<>();
//        HashMap<String, List<String>> cMap = new HashMap<>();
//        List<String> list = new ArrayList<>();
//        list.add("Curso1");
//        sMap.put("Curso", list);
//        cMap.put("Nome sala", list);
//
//        Calculator calculator = new Calculator(sMap, cMap, "Curso1;Curso1;Curso1");
//        List<Boolean> result = calculator.calculate();
//
//        assertNotNull(result, "O resultado não deve ser nulo");
//    }
//
//    @Test
//    public void testCalculateDate() {
//        HashMap<String, List<String>> sMap = new HashMap<>();
//        HashMap<String, List<String>> cMap = new HashMap<>();
//        List<String> list = new ArrayList<>();
//        list.add("2023-12-09");
//        sMap.put("Data da aula", list);
//        cMap.put("Nome sala", list);
//
//        Calculator calculator = new Calculator(sMap, cMap, "Data da aula;=;2023-12-09");
//        calculator.calculateDate();
//
//        assertFalse(calculator.list.contains(false), "A lista não deve conter false");
//    }
//
//    @Test
//    public void testCalculateTime() {
//        HashMap<String, List<String>> sMap = new HashMap<>();
//        HashMap<String, List<String>> cMap = new HashMap<>();
//        List<String> list = new ArrayList<>();
//        list.add("10:00");
//        sMap.put("Hora início da aula", list);
//        cMap.put("Nome sala", list);
//
//        Calculator calculator = new Calculator(sMap, cMap, "Hora início da aula;=;10:00");
//        calculator.calculateTime();
//
//        assertFalse(calculator.list.contains(false), "A lista não deve conter false");
//    }
//
//    @Test
//    public void testCalculateString() {
//        HashMap<String, List<String>> sMap = new HashMap<>();
//        HashMap<String, List<String>> cMap = new HashMap<>();
//        List<String> list = new ArrayList<>();
//        list.add("Curso1");
//        sMap.put("Curso", list);
//        cMap.put("Nome sala", list);
//
//        Calculator calculator = new Calculator(sMap, cMap, "Curso;=;Curso1");
//        calculator.calculateString();
//
//        assertFalse(calculator.list.contains(false), "A lista não deve conter false");
//    }
//
//    @Test
//    public void testCalculateInt() {
//        HashMap<String, List<String>> sMap = new HashMap<>();
//        HashMap<String, List<String>> cMap = new HashMap<>();
//        List<String> list = new ArrayList<>();
//        list.add("50");
//        sMap.put("Inscritos no turno", list);
//        cMap.put("Nome sala", list);
//
//        Calculator calculator = new Calculator(sMap, cMap, "Inscritos no turno;=;50");
//        calculator.calculateInt();
//
//        assertFalse(calculator.list.contains(false), "A lista não deve conter false");
//    }
}