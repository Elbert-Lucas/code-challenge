package br.com.hr_system.notification.repository;

import br.com.hr_system.notification.domain.Notification;
import br.com.hr_system.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findAllByUsers_OrToAllIsTrue(User user, Pageable pageable);
    Page<Notification> findAllByFrom(User user, Pageable pageable);
}
