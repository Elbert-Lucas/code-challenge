CREATE TABLE TB_CHAT(
    id SERIAL PRIMARY KEY NOT NULL,
    created_dth TIMESTAMP NOT NULL
);
CREATE TABLE TB_MESSAGE(
    id SERIAL PRIMARY KEY NOT NULL,
    message VARCHAR(500) NOT NULL,
    from_user INTEGER NOT NULL,
    chat INTEGER NOT NULL,
    sent_dth TIMESTAMP NOT NULL
);
CREATE TABLE TB_CHAT_USERS(
    chat_id INTEGER,
    user_id INTEGER,
    PRIMARY KEY (chat_id, user_id),
    CONSTRAINT tb_chat_user_pivot_chat_id FOREIGN KEY (chat_id) REFERENCES TB_CHAT(id),
    CONSTRAINT tb_notification_user_pivot_user_id FOREIGN KEY (user_id) REFERENCES TB_USER(id)
);