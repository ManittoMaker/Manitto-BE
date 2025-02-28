package manitto.backend.global.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

    @CreatedDate
    private Instant createdAt;

    protected static String generateFirestoreId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }
}
