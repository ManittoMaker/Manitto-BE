package manitto.backend;

import manitto.backend.config.mongo.EnableMongoTestServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableMongoTestServer
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
