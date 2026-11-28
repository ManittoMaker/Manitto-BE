package manitto.backend.domain.match.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
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

    public MatchResult findMatchResultByGroupIdAndGiverAndPassword(String groupId, String giver, String password) {
        Match match = findMatchByGroupId(groupId);

        MatchResult matchResult = match.getMatches().stream()
                .filter(result -> giver.equals(result.getGiver()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_MEMBER_NOT_FOUND));

        if (!password.equals(matchResult.getPassword())) {
            throw new CustomException(ErrorCode.MATCH_PASSWORD_MISMATCH);
        }

        return matchResult;
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
