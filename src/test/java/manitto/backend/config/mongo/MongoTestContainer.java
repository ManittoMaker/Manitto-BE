package manitto.backend.config.mongo;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public interface MongoTestContainer {

    @ServiceConnection
    MongoDBContainer MONGO = new MongoDBContainer(DockerImageName.parse("mongo:8.0.38"));
}
