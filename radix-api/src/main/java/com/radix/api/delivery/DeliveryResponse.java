package com.radix.api.delivery;

import java.time.Instant;

public record DeliveryResponse(
        Long id,
        Long feedId,
        Instant scheduledFor,
        Instant expectedAt,
        Instant createdAt
) {
    public static DeliveryResponse from(Delivery delivery) {
        return new DeliveryResponse(
                delivery.getId(),
                delivery.getFeed().getId(),
                delivery.getScheduledFor(),
                delivery.getExpectedAt(),
                delivery.getCreatedAt()
        );
    }
}