package com.radix.api.feed;

public record RegisterFeedRequest(
        String name,
        String source,
        String description,
        String format,
        String scheduleRule,
        String scheduleTimezone
) {}