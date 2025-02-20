package manitto.backend.domain.match.entity;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
@Getter
@Builder
public class Match {

    @Id
    private String id;
    private String groupId;
    private List<MatchResult> matches;


    public static Match create(String groupId, List<MatchResult> matches) {
        return Match.builder()
                .groupId(groupId)
                .matches(matches)
                .build();
    }
}
