package br.com.hr_system.notification.repository;

import br.com.hr_system.notification.domain.view.ResponseNotification;
import br.com.hr_system.user.domain.view.SimpleUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationResponseRepository extends JpaRepository<ResponseNotification, Integer> {

    Page<ResponseNotification> findAllByToOrToAllTrueOrderByIdDesc(SimpleUserDetails user, Pageable pageable);
    Page<ResponseNotification> findAllByFrom_OrderByIdDesc(SimpleUserDetails user, Pageable pageable);

}
