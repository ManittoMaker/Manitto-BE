package manitto.backend.global.repository;

import lombok.RequiredArgsConstructor;
import manitto.backend.global.entity.BaseEntity;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GlobalMongoTemplateRepository {

    private final MongoTemplate mongoTemplate;

    public <T extends BaseEntity> T saveWithoutDuplicatedId(T entity, Class<T> clazz) {
        int retry = 5;
        while (retry > 0) {
            Query query = new Query(Criteria.where("_id").is(entity.getId()));
            T existEntity = mongoTemplate.findOne(query, clazz);

            if (existEntity == null) {
                return mongoTemplate.save(entity);
            }

            entity.generateNewId();
            retry--;
        }
        throw new CustomException(ErrorCode.CRITICAL_ID_ERROR);
    }
}
