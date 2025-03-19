package manitto.backend.domain.group.repository;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.entity.GroupCount;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupCountTemplateRepository {

    private final MongoTemplate mongoTemplate;

    public int getTotalGroups() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("groupCount"));
        GroupCount groupCount = mongoTemplate.findOne(query, GroupCount.class);

        return (groupCount != null) ? groupCount.getTotalGroups() : 0;
    }
}
