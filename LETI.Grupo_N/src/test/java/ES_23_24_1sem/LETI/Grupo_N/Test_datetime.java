package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test_datetime {

    @Test
    public void testCompareToDate() {
        Date date1 = new Date("01/01/2022");
        Date date2 = new Date("01/01/2023");
        Date date3 = new Date("01/01/2022");

        assertTrue(date1.compareTo(date2) < 0, "date1 deve ser menor que date2");
        assertTrue(date2.compareTo(date1) > 0, "date2 deve ser maior que date1");
        assertEquals(0, date1.compareTo(date3), "date1 e date3 devem ser iguais");
    }

    @Test
    public void testEqualsDate() {
        Date date1 = new Date("01/01/2022");
        Date date2 = new Date("01/01/2023");
        Date date3 = new Date("01/01/2022");

        assertFalse(date1.equals(date2), "date1 e date2 não devem ser iguais");
        assertTrue(date1.equals(date3), "date1 e date3 devem ser iguais");
    }
    

    @Test
    public void testCompareToTime() {
        Time time1 = new Time("10:00:00");
        Time time2 = new Time("11:00:00");
        Time time3 = new Time("10:00:00");

        assertTrue(time1.compareTo(time2) < 0, "time1 deve ser menor que time2");
        assertTrue(time2.compareTo(time1) > 0, "time2 deve ser maior que time1");
        assertEquals(0, time1.compareTo(time3), "time1 e time3 devem ser iguais");
    }

    @Test
    public void testEqualsTime() {
        Time time1 = new Time("10:00:00");
        Time time2 = new Time("11:00:00");
        Time time3 = new Time("10:00:00");

        assertFalse(time1.equals(time2), "time1 e time2 não devem ser iguais");
        assertTrue(time1.equals(time3), "time1 e time3 devem ser iguais");
    }
}