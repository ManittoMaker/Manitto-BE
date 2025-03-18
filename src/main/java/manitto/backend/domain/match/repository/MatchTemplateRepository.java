package manitto.backend.domain.match.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchTemplateRepository {

    private final MongoTemplate mongoTemplate;

    public Match findMatchResultByGroupIdAndGiverAndPassword(String groupId, String giver, String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where("groupId").is(groupId));
        query.addCriteria(Criteria.where("matches")
                .elemMatch(
                        Criteria.where("giver").is(giver)
                                .and("password").is(password)
                ));
        query.fields().include("matches.$");
        List<Match> matches = mongoTemplate.find(query, Match.class);

        if (matches.isEmpty()) {
            throw new CustomException(ErrorCode.MATCH_NOT_FOUND);
        }
        if (matches.size() > 1) {
            throw new CustomException(ErrorCode.MATCH_INTEGRITY_VIOLATION);
        }

        return matches.get(0);
    }

    public Match findMatchByGroupId(String groupId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("groupId").is(groupId));
        List<Match> matches = mongoTemplate.find(query, Match.class);

        if (matches.isEmpty()) {
            throw new CustomException(ErrorCode.MATCH_NOT_FOUND);
        }
        if (matches.size() > 1) {
            throw new CustomException(ErrorCode.MATCH_INTEGRITY_VIOLATION);
        }

        return matches.get(0);
    }
}
