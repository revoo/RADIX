package com.radix.api.delivery;

import java.time.Instant;

public record ScheduleDeliveryRequest(Long feedId, Instant scheduledFor, Instant expectedAt) { }
