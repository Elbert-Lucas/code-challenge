package br.com.code_challenge.notification.repository;

import br.com.code_challenge.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
