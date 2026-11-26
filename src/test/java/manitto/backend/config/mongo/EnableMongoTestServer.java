package manitto.backend.config.mongo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportTestcontainers(MongoTestContainer.class)
public @interface EnableMongoTestServer {
}
