package manitto.backend.domain.match.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class MatchValidatorTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchValidator matchValidator;

    @Test
    void validateAlreadyExists_정상_이미_존재하는_매치가_아님() {
        // given
        String groupId = "groupId";

        // 존재하지 않음
        when(matchRepository.existsByGroupId(groupId))
                .thenReturn(false);

        // when

        // then
        assertThatCode(() -> matchValidator.validateAlreadyExists(groupId))
                .doesNotThrowAnyException();
    }

    @Test
    void validateAlreadyExists_에러_이미_존재하는_매치() {
        // given
        String groupId = "groupId";

        // 존재함
        when(matchRepository.existsByGroupId(groupId))
                .thenReturn(true);

        // when

        // then
        assertThatThrownBy(() -> matchValidator.validateAlreadyExists(groupId))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_ALREADY_EXIST);
    }

    @Test
    void validateDuplicateName_정상_겹치는_이름이_없음() {
        // given
        List<String> names = List.of("이름1", "이름2", "이름3");

        // when

        // then
        assertThatCode(() -> matchValidator.validateDuplicateName(names))
                .doesNotThrowAnyException();
    }

    @Test
    void validateDuplicateName_에러_중복된_이름이_있음() {
        // given
        List<String> names = List.of("중복된이름", "중복된이름", "이름3");

        // when

        // then
        assertThatThrownBy(() -> matchValidator.validateDuplicateName(names))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_MEMBER_NAME_DUPLICATED);
    }

    @Test
    void validateExists_정상_특정_그룹_id를_가진_매치가_존재함() {
        // given
        String groupId = "groupId";
        List<MatchResult> matchResults = List.of(
                MatchResult.create("giver1", "password1", "receiver1"),
                MatchResult.create("giver2", "password2", "receiver2"));

        when(matchRepository.getMatchByGroupId(groupId))
                .thenReturn(Match.create(groupId, matchResults));

        // when

        // then
        assertThatCode(() -> matchValidator.validateExists(groupId))
                .doesNotThrowAnyException();
    }

    @Test
    void validateExists_에러_특정_그룹_id를_가진_매치가_존재하지_않음() {
        // given
        String groupId = "groupId";
        when(matchRepository.getMatchByGroupId(groupId))
                .thenReturn(null);

        // when

        // then
        assertThatThrownBy(() -> matchValidator.validateExists(groupId))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_NOT_FOUND);
    }
}