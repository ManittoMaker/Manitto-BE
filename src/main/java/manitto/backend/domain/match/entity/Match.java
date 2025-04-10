package manitto.backend.domain.match.entity;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import manitto.backend.global.entity.BaseEntity;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Match extends BaseEntity {

    @Id
    private String id;
    @Indexed
    private String groupId;
    private List<MatchResult> matches;

    @Override
    public void generateNewId() {
        this.id = generateFirestoreId();
    }

    public static Match create(String groupId, List<MatchResult> matches) {
        if (matches == null || matches.size() < 2) {
            throw new CustomException(ErrorCode.MATCH_NOT_VALID);
        }

        return Match.builder()
                .id(generateFirestoreId())
                .groupId(groupId)
                .matches(matches)
                .build();
    }
}
