package com.radix.api.delivery;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> scheduleDelivery(
            @RequestBody ScheduleDeliveryRequest request
    ) {
        ScheduleDeliveryResult result =
                deliveryService.scheduleDelivery(
                        request.feedId(),
                        request.scheduledFor(),
                        request.expectedAt()
                );

        DeliveryResponse response =
                DeliveryResponse.from(result.delivery());

        if (result.created()) {
            URI location = URI.create(
                    "/deliveries/" + result.delivery().getId()
            );

            return ResponseEntity
                    .created(location)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public DeliveryResponse getDelivery(
            @PathVariable Long id
    ) {
        return DeliveryResponse.from(
                deliveryService.getDelivery(id)
        );
    }

    @PostMapping("/{deliveryId}/attempts")
    public ResponseEntity<DeliveryAttemptResponse> recordAttempt(
            @PathVariable Long deliveryId,
            @RequestBody RecordDeliveryAttemptRequest request
    ) {
        DeliveryAttempt attempt =
                deliveryService.recordAttempt(
                        deliveryId,
                        request.receivedAt(),
                        request.sizeBytes(),
                        request.checksum(),
                        request.originalFilename()
                );

        URI location = URI.create(
                "/deliveries/"
                        + deliveryId
                        + "/attempts/"
                        + attempt.getId()
        );

        return ResponseEntity
                .created(location)
                .body(DeliveryAttemptResponse.from(attempt));
    }

    @GetMapping("/{deliveryId}/attempts")
    public List<DeliveryAttemptResponse> getAttempts(
            @PathVariable Long deliveryId
    ) {
        return deliveryService
                .getAttempts(deliveryId)
                .stream()
                .map(DeliveryAttemptResponse::from)
                .toList();
    }
}