package com.radix.api.feed;

import com.radix.api.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Feed> getFeeds() {
        return feedRepository.findAll();
    }

    public Feed patchFeed(Long id, UpdateFeedRequest updateFeedRequest) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());

        // if exception wasn't thrown, feed was found and code will continue here


    }
}
