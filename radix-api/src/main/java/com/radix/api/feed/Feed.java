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

    // Domain Driven Design Mutators
    // update Feed name?
}

