package com.radix.api.feed;

public record UpdateFeedRequest(
        String name,
        String source,
        String description,
        String format,
        String scheduleRule,
        String scheduleTimezone
) {}
