package ru.matprojects.sqlexcel;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PostgreConnectionTest {
    @Test
    void testDbConn() throws SQLException {
        PostgreConnection psConn = new PostgreConnection();
        Connection conn = psConn.getConnection();
        assertNotNull(conn);

        conn.close();
    }
}