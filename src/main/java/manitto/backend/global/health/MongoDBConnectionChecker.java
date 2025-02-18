package manitto.backend.global.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MongoDBConnectionChecker {

    private final MongoTemplate mongoTemplate;

    public MongoDBConnectionChecker(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        checkConnection();
    }

    private void checkConnection() {
        try {
            mongoTemplate.getDb().listCollectionNames().first();
            log.info("MongoDB 연결 성공");
        } catch (Exception e) {
            log.info("MongoDB 연결 실패");
        }
    }
}
