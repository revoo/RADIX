  CREATE TABLE delivery (
    delivery_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    feed_id BIGINT NOT NULL,
    scheduled_for TIMESTAMPTZ NOT NULL,
    expeccted_at TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_delivery_feed
        FOREIGN KEY (feed_id)
        REFERENCES feed(feed_id),

    CONSTRAINT uq_delivery_feed_scheduled
        UNIQUE (feed_id, scheduled_for)
 );

 CREATE TABLE delivery_attempt (
     delivery_attempt_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     delivery_id BIGINT NOT NULL,
     received_at TIMESTAMPTZ NOT NULL,
     size_bytes BIGINT NOT NULL,
     checksum VARCHAR(128) NOT NULL,
     original_filename TEXT,
     created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_delivery_attempt_delivery
         FOREIGN KEY (delivery_id)
         REFERENCES delivery(delivery_id)
 );x``