package com.radix.api.delivery;

import java.time.Instant;

public record DeliveryAttemptResponse(
        Long id,
        Long deliveryId,
        Instant receivedAt,
        long sizeBytes,
        String checksum,
        String originalFilename,
        Instant createdAt
) {
    public static DeliveryAttemptResponse from(DeliveryAttempt attempt) {
        return new DeliveryAttemptResponse(
                attempt.getId(),
                attempt.getDelivery().getId(),
                attempt.getReceivedAt(),
                attempt.getSizeBytes(),
                attempt.getChecksum(),
                attempt.getOriginalFilename(),
                attempt.getCreatedAt()
        );
    }
}