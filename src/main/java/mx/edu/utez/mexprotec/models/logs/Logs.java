package mx.edu.utez.mexprotec.models.logs;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.users.Users;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Getter
@Setter
@NoArgsConstructor
public class Logs {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(columnDefinition = "json")
    @Type(JsonType.class)
    private JsonNode data;

    @Column(name = "method", columnDefinition = "TEXT NOT NULL")
    private String method;

    @Column(name = "request_uri", columnDefinition = "TEXT NOT NULL")
    private String requestUri;

    @Column(name = "ip", columnDefinition = "TEXT NOT NULL")
    private String ip;

    @Column(name = "user_agent",columnDefinition = "TEXT NOT NULL")
    private String userAgent;

    @Column(name = "status", columnDefinition = "SMALLINT NOT NULL")
    private int status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String createdAt;
}

