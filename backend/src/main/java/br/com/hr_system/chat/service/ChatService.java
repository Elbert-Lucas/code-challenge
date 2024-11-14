package br.com.hr_system.chat.service;

import br.com.hr_system.chat.domain.Message;
import br.com.hr_system.chat.dto.MessageDto;
import br.com.hr_system.chat.repository.MessageRepository;
import br.com.hr_system.user.repository.UserRepository;
import br.com.hr_system.user.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    public Page<Message> findMessageToUser(Pageable pageable){
        return messageRepository.findMessageToUser(userDetailsService.findLoggedUser(), userRepository.findById(2).get(), pageable);
    }
}
