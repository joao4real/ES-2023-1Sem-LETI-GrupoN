package ES_23_24_1sem.LETI.Grupo_N;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
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
    }
}