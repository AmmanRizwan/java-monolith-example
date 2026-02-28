package com.monolith.example.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ConnectionE2ETest {

    @LocalServerPort
    private int port;

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:14")
                    .withInitScript("query.sql");

    @Test
    public void testConnection() {
        String url = postgres.getJdbcUrl();
        String user = postgres.getUsername();
        String password = postgres.getPassword();

        var containerDelegate = new JdbcDatabaseDelegate(postgres, "");
        ScriptUtils.runInitScript(containerDelegate, "query.sql");

        System.out.println("Connecting to: " + url);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM custom_table WHERE id = 1");
            ResultSet resCount = stmt.executeQuery("SELECT COUNT(*) FROM custom_table");

            int count = resCount.getInt(1);

            Assertions.assertTrue(rs.next(), "Should have found a record");
            Assertions.assertEquals("Amman", rs.getString("name"));
            Assertions.assertEquals(1, count);

            System.out.println("Successfully Execute the PostgreSQL Database Query.");
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
        }


    }
}
