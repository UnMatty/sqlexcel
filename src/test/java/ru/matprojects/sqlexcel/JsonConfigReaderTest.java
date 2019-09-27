package ru.matprojects.sqlexcel;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonConfigReaderTest {

    @Test
    void readJdbcPar() {
        JsonConfigReader jsReader = new JsonConfigReader();
        Map<String, Object> pars = jsReader.readJdbcPar();

        assertFalse(pars.isEmpty());
    }
}