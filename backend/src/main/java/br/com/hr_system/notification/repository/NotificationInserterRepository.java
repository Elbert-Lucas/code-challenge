package br.com.hr_system.notification.repository;

import br.com.hr_system.notification.domain.Notification;
import br.com.hr_system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class NotificationInserterRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert notificationInserter;


    private final String INSERT_PIVOT_QUERY =  " INSERT INTO tb_notification_user(notification_id, user_id) VALUES ";
    private final String VALUES = " (?, ?) ";

    @Autowired
    public NotificationInserterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.notificationInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("tb_notification").usingGeneratedKeyColumns("id");
    }

    public Integer insertNotification(Notification notification){

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_from_id", notification.getFrom().getId());
        parameters.put("title", notification.getTitle());
        parameters.put("message", notification.getMessage());
        parameters.put("to_all", notification.getToAll());
        parameters.put("created_dth", new Date());

        Number id =  notificationInserter.executeAndReturnKey(parameters);
        if(!notification.getToAll()) insertUsersOnNotification(id, notification);
        return (Integer) id;
    }
    private boolean insertUsersOnNotification(Number notificationId,Notification notification){
        return jdbcTemplate.update(this.createInsertPivotQuery(notificationId, notification)) > 0;
    }
    private String createInsertPivotQuery(Number notificationId, Notification notification){
        StringBuilder query = new StringBuilder(INSERT_PIVOT_QUERY);
        List<User> users = notification.getUsers();

        users.forEach(user -> {
            query.append(VALUES.replaceFirst("\\?", String.valueOf(notificationId)).replaceFirst("\\?", String.valueOf(user.getId())));
            if(users.get(users.size() - 1).equals(user)) query.append(";");
            else query.append(",");
        });
        return query.toString();
    }
}
