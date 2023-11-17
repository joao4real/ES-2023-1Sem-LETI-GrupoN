package ES_23_24_1sem.LETI.Grupo_N;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class testClassrrom {

    @Test
    void testCreateClassroomsInfoByLocalFile() {
        String filePath = "Testes_JUnit.csv";
        ClassroomsInfo ci = ClassroomsInfo.createClassroomsInfoByLocalFile(filePath);
        assertNotNull(ci);
    }

    @Test
    void testCreateClassroomsInfoByRemoteFile() {
        String url = "https://raw.githubusercontent.com/joao4real/ES-2023-1Sem-LETI-GrupoN/main/CaracterizacaoSalas.csv";
        ClassroomsInfo ci = ClassroomsInfo.createClassroomsInfoByRemoteFile(url);
        assertNotNull(ci);
    }
}