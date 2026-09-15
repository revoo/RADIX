package com.radix.api.feed;

import jakarta.persistence.*;

@Entity
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="feed_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "description")
    private String description;

    @Column(name = "format", nullable = false)
    private String format;

    @Column(name = "schedule_rule", nullable = false)
    private String scheduleRule;

    @Column(name = "schedule_timezone", nullable = false)
    private String scheduleTimezone;

    // JPA needs no-arg constructor
    protected Feed() {}

    public Feed(
            String name,
            String source,
            String description,
            String format,
            String scheduleRule,
            String scheduleTimezone) {
        this.name = name;
        this.source = source;
        this.description = description;
        this.format = format;
        this.scheduleRule = scheduleRule;
        this.scheduleTimezone = scheduleTimezone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public String getFormat() {
        return format;
    }

    public String getScheduleRule() {
        return scheduleRule;
    }

    public String getScheduleTimezone() {
        return scheduleTimezone;
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid feed name!");
        }
        this.name = name;
    }

    public void changeSource(String source) {
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("Invalid source!");
        }
        this.source = source;
    }

    public void changeDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Invalid description!");
        }
        this.description = description;
    }

    public void changeFormat(String format) {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Invalid format!");
        }
        this.format = format;
    }

    public void changeScheduleRule(String scheduleRule) {
        if (scheduleRule == null || scheduleRule.isBlank()) {
            throw new IllegalArgumentException("Invalid schedule rule!");
        }
        this.scheduleRule = scheduleRule;
    }

    public void changeScheduleTimezone(String scheduleTimezone) {
        if (scheduleRule == null || scheduleRule.isBlank()) {
            throw new IllegalArgumentException("Invalid schedule timezone!");
        }
        this.scheduleTimezone = scheduleTimezone;
    }
}

