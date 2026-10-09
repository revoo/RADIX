package com.radix.api.delivery;

import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryTest {

    @Test
    void rejectsMissingFeed() {
        Instant time = Instant.parse("2026-10-09T14:00:00Z");

        assertThrows(IllegalArgumentException.class, () -> new Delivery(null, time, time));
    }
}
