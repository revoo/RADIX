package com.radix.api.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByFeedIdAndScheduledFor(Long feedId, Instant scheduledFor);
}
