package com.radix.api.delivery;

import java.time.Instant;

public record RecordDeliveryAttemptRequest (Instant receivedAt, long sizeBytes, String checksum, String originalFilename) {}
