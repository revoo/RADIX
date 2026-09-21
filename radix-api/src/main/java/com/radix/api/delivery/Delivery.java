package com.radix.api.delivery;

import com.radix.api.feed.Feed;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    @Column(name = "scheduled_for", nullable = false, updatable = false)
    private Instant scheduledFor;

    @Column(name = "expected_at", nullable = false)
    private Instant expectedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Delivery() {
        // Required by JPA
    }

    public Delivery(
            Feed feed,
            Instant scheduledFor,
            Instant expectedAt
    ) {
        if (feed == null) {
            throw new IllegalArgumentException("Feed is required");
        }

        if (scheduledFor == null) {
            throw new IllegalArgumentException("Scheduled time is required");
        }

        if (expectedAt == null) {
            throw new IllegalArgumentException("Expected arrival time is required");
        }

        this.feed = feed;
        this.scheduledFor = scheduledFor;
        this.expectedAt = expectedAt;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Feed getFeed() {
        return feed;
    }

    public Instant getScheduledFor() {
        return scheduledFor;
    }

    public Instant getExpectedAt() {
        return expectedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void reviseExpectedAt(Instant expectedAt) {
        if (expectedAt == null) {
            throw new IllegalArgumentException("Expected arrival time is required");
        }

        this.expectedAt = expectedAt;
    }
}