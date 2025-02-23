package manitto.backend.domain.match.repository;

import manitto.backend.domain.match.entity.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String> {

}
