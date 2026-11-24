package manitto.backend.config.mongo;

import de.flapdoodle.embed.mongo.spring.autoconfigure.EmbeddedMongoAutoConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.test.context.TestPropertySource;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration(EmbeddedMongoAutoConfiguration.class)
@TestPropertySource(properties = "de.flapdoodle.mongodb.embedded.version=8.0.23")
public @interface EnableMongoTestServer {
}
