package br.com.hr_system.chat;

import br.com.hr_system.chat.domain.Message;
import br.com.hr_system.chat.dto.MessageDto;
import br.com.hr_system.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping
    public Page<Message> getMessages(Pageable pageable){
        return chatService.findMessageToUser(pageable);

    }
}
