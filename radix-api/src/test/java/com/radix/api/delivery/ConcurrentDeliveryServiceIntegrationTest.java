
package com.radix.api.delivery;

import com.radix.api.feed.Feed;
import com.radix.api.feed.FeedRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.time.Instant;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ConcurrentDeliveryServiceIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    FeedRepository feedRepository;

    private Long feedId;

    record Outcome(Long deliveryId, boolean created) {}

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
    void concurrentSchedulingCreatesOnlyOneDelivery() throws Exception {

        Instant scheduledFor = Instant.parse("2026-10-18T14:00:00Z");

        Instant expectedAt = Instant.parse("2026-10-18T14:30:00Z");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        CountDownLatch ready = new CountDownLatch(2);
        CountDownLatch start = new CountDownLatch(1);

        Callable<Outcome> request = () -> {
            ready.countDown();

            if (!start.await(10, TimeUnit.SECONDS)) {
                throw new TimeoutException("Start gate timed out");
            }

            var result = deliveryService.scheduleDelivery(feedId, scheduledFor, expectedAt);

            return new Outcome(result.delivery().getId(), result.created());
        };

        try {
            Future<Outcome> workerA = executor.submit(request);
            Future<Outcome> workerB = executor.submit(request);

            // Wait until both workers are ready.
            assertTrue(ready.await(10, TimeUnit.SECONDS));

            // Release both workers.
            start.countDown();

            Outcome a = workerA.get(15, TimeUnit.SECONDS);
            Outcome b = workerB.get(15, TimeUnit.SECONDS);

            // Both should receive the same Delivery.
            assertEquals(a.deliveryId(), b.deliveryId());

            // Exactly one worker should create it.
            assertNotEquals(a.created(), b.created());

            // Verify the actual database state.
            assertEquals(1, deliveryRepository.count());

        } finally {
            start.countDown();
            executor.shutdownNow();
        }
    }
}
