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

    public Group findGroupByLeaderNameAndGroupNameAndPassword(String leaderName, String groupName, String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where("leaderName").is(leaderName));
        query.addCriteria(Criteria.where("groupName").is(groupName));
        List<Group> groups = mongoTemplate.find(query, Group.class);

        if (groups.isEmpty()) {
            throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        }

        List<Group> passwordMatchedGroups = groups.stream()
                .filter(group -> password.equals(group.getPassword()))
                .toList();

        if (passwordMatchedGroups.isEmpty()) {
            throw new CustomException(ErrorCode.GROUP_PASSWORD_MISMATCH);
        }
        if (passwordMatchedGroups.size() > 1) {
            throw new CustomException(ErrorCode.GROUP_INTEGRITY_VIOLATION);
        }

        return passwordMatchedGroups.get(0);
    }
}
