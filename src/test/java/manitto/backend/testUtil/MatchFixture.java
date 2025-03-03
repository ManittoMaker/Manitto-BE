package manitto.backend.testUtil;

import java.util.List;
import manitto.backend.domain.match.entity.MatchResult;

public class MatchFixture {

    public static List<MatchResult> createCorrectMatchResultListFixture() {
        MatchResult matchResult1 = MatchResult.create("giver", "password", "receiver");
        MatchResult matchResult2 = MatchResult.create("receiver", "password", "giver");
        return List.of(matchResult1, matchResult2);
    }
}
