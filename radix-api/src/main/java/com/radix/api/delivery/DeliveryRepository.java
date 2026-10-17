package com.radix.api.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByFeedIdAndScheduledFor(Long feedId, Instant scheduledFor);

    @Modifying
    @Query(value = """
    INSERT INTO delivery (feed_id, scheduled_for, expected_at)
    VALUES (:feedId, :scheduledFor, :expectedAt)
    ON CONFLICT (feed_id, scheduled_for) DO NOTHING
    """, nativeQuery = true)
    int insertIfAbsent(@Param("feedId") Long feedId, @Param("scheduledFor") Instant scheduledFor, @Param("expectedAt") Instant expectedAt);
}
