package manitto.backend.domain.group.repository;

import manitto.backend.domain.group.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    Group getGroupByLeaderNameAndGroupNameAndPassword(String groupName, String LeaderName, String password);
}
