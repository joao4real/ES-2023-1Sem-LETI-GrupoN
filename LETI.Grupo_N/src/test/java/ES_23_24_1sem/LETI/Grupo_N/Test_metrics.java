package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Test_metrics {

    private UserMetricsAnalyser userMetricsAnalyser;
    private LinkedHashMap<String, List<String>> sMap;
    private LinkedHashMap<String, List<String>> cMap;

    @BeforeEach
    public void setUp() {
        sMap = new LinkedHashMap<>();
        cMap = new LinkedHashMap<>();
        List<String> list = new ArrayList<>();
        list.add("test");
        sMap.put("testKey", list);
        cMap.put("testKey", list);
        userMetricsAnalyser = new UserMetricsAnalyser(sMap, cMap);
    }

    @Test
    public void testGetFields() {
        String[] fields = userMetricsAnalyser.getFields();
        assertEquals(3, fields.length);
        assertEquals("--", fields[0]);
        assertEquals("testKey", fields[1]);
        assertEquals("testKey", fields[2]);
    }

    @Test
    public void testInitialize() {
        assertNotNull(userMetricsAnalyser);
    }


}