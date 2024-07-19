CREATE VIEW VW_LOGGED_USER AS
    SELECT user_details.*, r.role
    FROM TB_USER u
		LEFT JOIN tb_role r ON u.role_id = r.id
		LEFT JOIN VW_USER_DETAILS user_details ON user_details.id = u.id;
