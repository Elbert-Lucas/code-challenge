package br.com.hr_system.session.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("Session")
@Getter @Setter
@Builder @ToString
public class Session implements Serializable {
    @Id
    private String sessionId;
    @Indexed
    private String userId;
}
