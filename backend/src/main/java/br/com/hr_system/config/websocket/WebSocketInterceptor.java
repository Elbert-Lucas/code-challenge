package br.com.hr_system.config.websocket;

import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.session.service.SessionService;
import br.com.hr_system.user.domain.view.LoggedUserDetails;
import br.com.hr_system.user.service.UserDetailsService;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Component
public class WebSocketInterceptor  extends DefaultHandshakeHandler implements WebSocketHandlerDecoratorFactory, ChannelInterceptor {

    private final SessionService sessionService;

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private LoggedUserDetails user;

    private String sessionId;

    @Autowired
    public WebSocketInterceptor(SessionService sessionService, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.sessionService = sessionService;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        SimpMessageType messageType = accessor.getMessageType();
        if(messageType.equals(SimpMessageType.CONNECT)) {
            authenticateConnection(accessor);
            sessionService.saveSession(this.sessionId, String.valueOf(this.user.getId()));
        }

        return message;
    }

    private void authenticateConnection(StompHeaderAccessor accessor){
        String jwt = accessor.getNativeHeader("Authorization")
                .get(0)
                .substring(7);
        user = userDetailsService.findUserByToken(jwt);
        jwtUtil.validateToken(jwt, user);

    }
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        this.sessionId = UUID.randomUUID().toString();
        return new UserPrincipal(this.sessionId);
    }

    @Override
    public WebSocketHandler decorate(WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler){
            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                sessionService.finishSession(session.getPrincipal().getName());
            }
        };
    }

}
