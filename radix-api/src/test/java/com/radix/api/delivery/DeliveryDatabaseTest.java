package com.radix.api.delivery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
class DeliveryDatabaseTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void flywayCreatesDeliveryTables() {
        Integer deliveries = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM delivery", Integer.class);
        Integer attempts = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM delivery_attempt", Integer.class);

        assertEquals(0, deliveries);
        assertEquals(0, attempts);
    }
}
