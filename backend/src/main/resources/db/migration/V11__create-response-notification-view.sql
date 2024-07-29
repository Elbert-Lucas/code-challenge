CREATE VIEW VW_RESPONSE_NOTIFICATION AS
	SELECT notification.id, notification.title, notification.message, notification.created_dth,
		   fromUser.id as from_user_id, notification.to_all
	FROM TB_NOTIFICATION notification
	LEFT JOIN TB_USER fromUser ON fromUser.id = notification.user_from_id;