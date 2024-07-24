CREATE TABLE TB_NOTIFICATION(
    id SERIAL PRIMARY KEY NOT NULL,
    user_from_id INTEGER NOT NULL,
    title VARCHAR(60) NOT NULL,
    message VARCHAR(255) NOT NULL,
    to_all BOOLEAN NOT NULL,
    CONSTRAINT tb_notification_user_id FOREIGN KEY (user_from_id) REFERENCES TB_USER(id)
);

CREATE TABLE TB_NOTIFICATION_USER (
    notification_id INTEGER,
    user_id INTEGER,
    PRIMARY KEY (notification_id, user_id),
    CONSTRAINT tb_notification_user_pivot_notification_id FOREIGN KEY (notification_id) REFERENCES TB_NOTIFICATION(id),
    CONSTRAINT tb_notification_user_pivot_user_id FOREIGN KEY (user_id) REFERENCES TB_USER(id)
);