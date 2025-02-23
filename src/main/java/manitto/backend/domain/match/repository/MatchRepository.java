package manitto.backend.domain.match.repository;

import java.util.List;
import manitto.backend.domain.match.entity.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MatchRepository extends MongoRepository<Match, String> {

    @Query(value = "{ 'groupId': ?0, 'matches': { '$elemMatch': { 'giver': ?1, 'password': ?2 } } }",
            fields = "{ 'matches.$': 1 }")
    List<Match> findMatchResultByGroupIdAndGiverAndPassword(String groupId, String giver, String password);
}
