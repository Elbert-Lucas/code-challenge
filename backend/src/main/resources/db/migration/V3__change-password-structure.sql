ALTER TABLE TB_USER
DROP CONSTRAINT tb_user_password_fk;

ALTER TABLE TB_USER
DROP COLUMN password_id;

ALTER TABLE TB_USER
ADD COLUMN password VARCHAR(255);

DROP TABLE TB_PASSWORD