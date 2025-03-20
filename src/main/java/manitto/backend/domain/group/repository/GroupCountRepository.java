package manitto.backend.domain.group.repository;

import manitto.backend.domain.group.entity.GroupCount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupCountRepository extends MongoRepository<GroupCount, String> {
}
