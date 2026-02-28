package com.monolith.example.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class HomeE2ETest {

    @LocalServerPort
    private int port;

    @Container
    private static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:14")
                    .withDatabaseName("test")
                    .withUsername("admin")
                    .withPassword("password");

    @Test
    public void testConnection() {
        String url = postgres.getJdbcUrl();
        String user = postgres.getUsername();
        String password = postgres.getPassword();

        System.out.println("Connecting to: " + url);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE random_table (id SERIAL PRIMARY KEY, name VARCHAR(50))");
            stmt.execute("INSERT INTO random_table (name) VALUES ('Amman')");

            ResultSet rs = stmt.executeQuery("SELECT name FROM random_table WHERE id = 1");

            Assertions.assertTrue(rs.next(), "Should have found a record");
            Assertions.assertEquals("Amman", rs.getString("name"));

            System.out.println("Successfully Execute the PostgreSQL Database Query.");
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
        }


    }
}
