package manitto.backend.domain.group.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupTemplateRepository {

    private final MongoTemplate mongoTemplate;

    public Group findGroupByGroupNameAndLeaderNameAndPassword(String groupName, String leaderName, String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where("groupName").is(groupName));
        query.addCriteria(Criteria.where("leaderName").is(leaderName));
        query.addCriteria(Criteria.where("password").is(password));
        List<Group> groups = mongoTemplate.find(query, Group.class);

        if (groups.isEmpty()) {
            throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        }
        if (groups.size() > 1) {
            throw new CustomException(ErrorCode.GROUP_INTEGRITY_VIOLATION);
        }

        return groups.get(0);
    }
}
