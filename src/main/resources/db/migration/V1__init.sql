-- V1__create_orders_table.sql
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    total NUMERIC(10,4) NOT NULL,
    tracking VARCHAR(255) NOT NULL UNIQUE,
    date_receive TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0
);

-- V2__create_items_table.sql
CREATE TABLE item (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    value NUMERIC(10,4) NOT NULL,
    total NUMERIC(10,4) NOT NULL,
    order_id BIGINT,
    CONSTRAINT fk_order FOREIGN KEY (order_id)
        REFERENCES orders(id)
);

-- V3__create_indexes.sql
CREATE INDEX idx_item_order_id ON item(order_id);
CREATE INDEX idx_orders_code ON orders(code);
CREATE INDEX idx_orders_tracking ON orders(tracking);
CREATE INDEX idx_item_code ON item(code);