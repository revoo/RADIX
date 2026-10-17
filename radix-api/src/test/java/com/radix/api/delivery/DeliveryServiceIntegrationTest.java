package com.radix.api.delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

import com.radix.api.feed.*;

@SpringBootTest
@Testcontainers
@Transactional
class DeliveryServiceIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    private FeedRepository feedRepository;

    private Long feedId;

    @BeforeEach
    void setUp() {
        Feed feed = new Feed(
                "Daily Orders",
                "Vendor A",
                "Test feed",
                "CSV",
                "DAILY",
                "America/Chicago"
        );

        feedId = feedRepository.save(feed).getId();
    }

    @Test
    void schedulingSameOccurrenceTwiceCreatesOneDelivery() {
        Instant scheduledFor = Instant.parse("2026-10-10T14:00:00Z");
        Instant expectedAt = Instant.parse("2026-10-10T14:30:00Z");

        var first = deliveryService.scheduleDelivery(feedId, scheduledFor, expectedAt);
        var second = deliveryService.scheduleDelivery(feedId, scheduledFor, expectedAt);

        assertTrue(first.created());
        assertFalse(second.created());

        assertEquals(first.delivery().getId(), second.delivery().getId());

        assertEquals(1, deliveryRepository.count());
    }
}
