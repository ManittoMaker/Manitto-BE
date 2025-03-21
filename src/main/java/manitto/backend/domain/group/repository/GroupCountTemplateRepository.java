package manitto.backend.domain.group.repository;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.entity.GroupCount;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupCountTemplateRepository {

    private final MongoTemplate mongoTemplate;

    public void updateTotalGroups() {
        Query query = new Query(Criteria.where("id").is("groupCount"));
        Update update = new Update().inc("totalGroups", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        mongoTemplate.findAndModify(query, update, options, GroupCount.class);
    }

    public int getTotalGroups() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("groupCount"));
        GroupCount groupCount = mongoTemplate.findOne(query, GroupCount.class);

        return (groupCount != null) ? groupCount.getTotalGroups() : 0;
    }
}
