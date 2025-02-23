package manitto.backend.domain.match.repository;

import java.util.List;
import manitto.backend.domain.match.entity.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MatchRepository extends MongoRepository<Match, String> {

    @Query(value = "{ 'groupId': ?0 }",
            fields = "{ 'matches': { '$filter': { 'input': '$matches', 'as': 'match', 'cond': { '$and': [ { '$eq': ['$$match.giver', ?1] }, { '$eq': ['$$match.password', ?2] } ] } } } }")
    List<Match> findMatchResultByGroupIdAndGiverAndPassword(String groupId, String giver, String password);
}
