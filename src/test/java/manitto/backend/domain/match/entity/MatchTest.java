package manitto.backend.domain.match.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("unit")
class MatchTest {

    @Test
    void create_내부_빌더를_통해_정상적으로_생성됨() {
        // given
        String groupId = "groupId";
        MatchResult matchResult1 = MatchResult.create("giver", "password", "receiver");
        MatchResult matchResult2 = MatchResult.create("receiver", "password", "giver");
        List<MatchResult> matchResults = List.of(matchResult1, matchResult2);

        // when
        Match match = Match.create(groupId, matchResults);

        // then
        assertThat(match).isNotNull();
        assertThat(match.getGroupId()).isEqualTo(groupId);
        assertThat(match.getMatches().size()).isEqualTo(2);
        assertThat(match.getId()).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void create_MatchResult가_2개_이상_들어오지_않으면_에러(int size) {
        // given
        String groupId = "groupId";
        MatchResult matchResult = MatchResult.create("giver", "password", "receiver");
        List<MatchResult> matchResults = (size == 0) ? null : List.of(matchResult);

        // when

        // then
        assertThatThrownBy(() -> Match.create(groupId, matchResults))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_NOT_VALID);
    }
}