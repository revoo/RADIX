package com.radix.api.feed;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/feeds")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping
    public ResponseEntity<Feed> registerFeed(@RequestBody RegisterFeedRequest request) {
        Feed createdFeed = feedService.registerFeed(
                request.name(),
                request.source(),
                request.description(),
                request.format(),
                request.scheduleRule(),
                request.scheduleTimezone()
        );

        URI location = URI.create("/feeds/" + createdFeed.getId());
        return ResponseEntity.created(location).body(createdFeed);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feed> getFeed(@PathVariable("id") Long id) {
        return ResponseEntity.of(feedService.getFeed(id));
    }

    @GetMapping
    public ResponseEntity<List<Feed>> getFeeds() {
        return ResponseEntity.ok(feedService.getFeeds());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Feed> patchFeed(@PathVariable("id") Long id, @RequestBody UpdateFeedRequest request) {
        return ResponseEntity.ok(feedService.patchFeed(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeed(@PathVariable("id") Long id) {
        feedService.deleteFeed(id);
        return ResponseEntity.noContent().build();
    }

}
