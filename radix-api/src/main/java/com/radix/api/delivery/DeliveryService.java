package com.radix.api.delivery;

import com.radix.api.exceptions.ResourceNotFoundException;
import com.radix.api.feed.Feed;
import com.radix.api.feed.FeedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private  final DeliveryAttemptRepository deliveryAttemptRepository;
    private final FeedRepository feedRepository;

    public DeliveryService(
            DeliveryRepository deliveryRepository,
            DeliveryAttemptRepository deliveryAttemptRepository,
            FeedRepository feedRepository
    ) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryAttemptRepository = deliveryAttemptRepository;
        this.feedRepository = feedRepository;
    }

    @Transactional
    public ScheduleDeliveryResult scheduleDelivery(Long feedId, Instant scheduledFor, Instant expectedAt) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new ResourceNotFoundException("Feed " + feedId + " was not found"));

        Optional<Delivery> existing = deliveryRepository.findByFeedIdAndScheduledFor(feedId, scheduledFor);

        if (existing.isPresent()) {
            return new ScheduleDeliveryResult(existing.get(), false);
        }

        Delivery delivery = deliveryRepository.save(new Delivery(feed, scheduledFor, expectedAt));

        return new ScheduleDeliveryResult(delivery, true);
    }

    @Transactional
    public DeliveryAttempt recordAttempt(
            Long deliveryId,
            Instant receivedAt,
            long sizeBytes,
            String checksum,
            String originalFilename
    ) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Delivery " + deliveryId + " was not found")
                );

        DeliveryAttempt attempt = new DeliveryAttempt(
                delivery,
                receivedAt,
                sizeBytes,
                checksum,
                originalFilename
        );

        return deliveryAttemptRepository.save(attempt);
    }

    @Transactional(readOnly = true)
    public Delivery getDelivery(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Delivery " + id + " was not found")
                );
    }

    @Transactional(readOnly = true)
    public List<DeliveryAttempt> getAttempts(Long deliveryId) {
        if (!deliveryRepository.existsById(deliveryId)) {
            throw new ResourceNotFoundException("Delivery " + deliveryId + " was not found");
        }

        return deliveryAttemptRepository.findByDeliveryIdOrderByReceivedAtAsc(deliveryId);
    }
}
