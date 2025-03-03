package manitto.backend.global.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity implements Persistable<String> {

    @CreatedDate
    @Indexed(name = "ttl", expireAfter = "30d") // TTL 한달
    private Instant createdAt;

    public abstract String getId();

    public abstract void generateNewId();

    protected static String generateFirestoreId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}
