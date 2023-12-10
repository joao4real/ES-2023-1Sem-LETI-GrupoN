package ES_23_24_1sem.LETI.Grupo_N;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test class for testing the functionality of the ClassroomsInfo class.
 */
class testClassroom {

	/**
     * Tests the createClassroomsInfoByLocalFile method of the ClassroomsInfo class.
     * <p>
     * This test checks if the createClassroomsInfoByLocalFile method is able to create a ClassroomsInfo object
     * from a local CSV file. The test asserts that the resulting object is not null.
     */
    @Test
    void testCreateClassroomsInfoByLocalFile() {
        String filePath = "Testes_JUnit.csv";
        ClassroomsInfo ci = ClassroomsInfo.createClassroomsInfoByLocalFile(filePath);
        assertNotNull(ci);
    }

    /**
     * Tests the createClassroomsInfoByRemoteFile method of the ClassroomsInfo class.
     * <p>
     * This test checks if the createClassroomsInfoByRemoteFile method is able to create a ClassroomsInfo object
     * from a remote CSV file. The test asserts that the resulting object is not null.
     */
    @Test
    void testCreateClassroomsInfoByRemoteFile() {
        String url = "https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/CaracterizacaoSalas.csv";
        ClassroomsInfo ci = ClassroomsInfo.createClassroomsInfoByRemoteFile(url);
        assertNotNull(ci);
    }
}