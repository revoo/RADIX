package com.radix.api.delivery;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "delivery_attempt")
public class DeliveryAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_attempt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_id", nullable = false, updatable = false)
    private Delivery delivery;

    @Column(name = "received_at", nullable = false, updatable = false)
    private Instant receivedAt;

    @Column(name = "size_bytes", nullable = false, updatable = false)
    private long sizeBytes;

    @Column(name = "checksum", nullable = false, updatable = false, length = 128)
    private String checksum;

    @Column(name = "original_filename", updatable = false)
    private String originalFilename;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected DeliveryAttempt() {
        // Required by JPA
    }

    public DeliveryAttempt(
            Delivery delivery,
            Instant receivedAt,
            long sizeBytes,
            String checksum,
            String originalFilename
    ) {
        if (delivery == null) {
            throw new IllegalArgumentException("Delivery is required");
        }

        if (receivedAt == null) {
            throw new IllegalArgumentException("Received time is required");
        }

        if (sizeBytes < 0) {
            throw new IllegalArgumentException("File size cannot be negative");
        }

        if (checksum == null || checksum.isBlank()) {
            throw new IllegalArgumentException("Checksum is required");
        }

        this.delivery = delivery;
        this.receivedAt = receivedAt;
        this.sizeBytes = sizeBytes;
        this.checksum = checksum;
        this.originalFilename = originalFilename;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}