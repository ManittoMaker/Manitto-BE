package manitto.backend.domain.match.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.List;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import manitto.backend.testUtil.MatchFixture;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class MatchTemplateRepositoryTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MatchTemplateRepository matchTemplateRepository;

    @Test
    void findMatchResultByGroupIdAndGiverAndPassword_정상_조회() {
        // given
        String groupId = "groupId";
        String giver = "giver";
        String password = "password";

        List<MatchResult> matchResults = MatchFixture.createCorrectMatchResultListFixture();
        Match match = Match.create(groupId, matchResults);
        // 정상 객체를 반환했다고 가정
        when(mongoTemplate.find(any(Query.class), eq(Match.class)))
                .thenReturn(List.of(match));

        // when
        Match result = matchTemplateRepository.findMatchResultByGroupIdAndGiverAndPassword(
                groupId, giver, password);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void findMatchResultByGroupIdAndGiverAndPassword_에러_조회_실패() {
        // given
        String groupId = "groupId";
        String giver = "giver";
        String password = "password";

        // 빈 객체 반환
        when(mongoTemplate.find(any(Query.class), eq(Match.class)))
                .thenReturn(List.of());

        // when

        // then
        assertThatThrownBy(
                () -> matchTemplateRepository.findMatchResultByGroupIdAndGiverAndPassword(groupId, giver, password))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_NOT_FOUND);
    }

    @Test
    void findMatchResultByGroupIdAndGiverAndPassword_에러_하나의_그룹이_2개_이상의_매치_정보를_가지고_있음() {
        // given
        String groupId = "groupId";
        String giver = "giver";
        String password = "password";

        List<MatchResult> matchResults = MatchFixture.createCorrectMatchResultListFixture();
        Match match1 = Match.create(groupId, matchResults);
        Match match2 = Match.create(groupId, matchResults);
        // 2개 이상 반환
        when(mongoTemplate.find(any(Query.class), eq(Match.class)))
                .thenReturn(List.of(match1, match2));

        // when

        // then
        assertThatThrownBy(
                () -> matchTemplateRepository.findMatchResultByGroupIdAndGiverAndPassword(groupId, giver, password))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_INTEGRITY_VIOLATION);
    }
}