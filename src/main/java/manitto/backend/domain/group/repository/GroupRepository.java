package manitto.backend.domain.group.repository;

import manitto.backend.domain.group.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}
