package br.com.hr_system.config.websocket;

import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.session.service.SessionService;
import br.com.hr_system.user.domain.view.LoggedUserDetails;
import br.com.hr_system.user.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConnectAuthInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private final SessionService sessionService;
    
    @Autowired
    public ConnectAuthInterceptor(JwtUtil jwtUtil, UserDetailsService userDetailsService, SessionService sessionService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.sessionService = sessionService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        SimpMessageType messageType = accessor.getMessageType();
        if(messageType.equals(SimpMessageType.CONNECT)) {
            String jwt = accessor.getNativeHeader("Authorization")
                                 .get(0)
                                 .substring(7);

            LoggedUserDetails user = userDetailsService.findUserByToken(jwt);
            jwtUtil.validateToken(jwt, user);
        }
        return message;
    }
}
