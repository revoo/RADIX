package com.radix.api.feed;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedService {

    private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public Feed registerFeed(
            String name,
            String source,
            String description,
            String format,
            String scheduleRule,
            String scheduleTimezone) {

        Feed feed = new Feed(
                name,
                source,
                description,
                format,
                scheduleRule,
                scheduleTimezone
        );

        return feedRepository.save(feed);
    }

    public Optional<Feed> getFeed(Long id) {
        return feedRepository.findById(id);
    }
}
