package br.com.hr_system.chat.repository;

import br.com.hr_system.chat.domain.Chat;
import br.com.hr_system.chat.domain.Message;
import br.com.hr_system.chat.dto.MessageDto;
import br.com.hr_system.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Chat, Integer> {
    @Query(
            value = "SELECT chat.messages FROM TB_CHAT chat " +
                    "WHERE (:from MEMBER OF chat.users AND :to MEMBER OF chat.users) " +
                    "AND SIZE(chat.users) = 2"
    )
    Page<Message> findMessageToUser(User from, User to, Pageable pageable);
}
