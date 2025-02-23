package manitto.backend.domain.match.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import manitto.backend.testUtil.MatchDtoMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

@DataMongoTest
@Import(MatchService.class)
class MatchServiceTest {

    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchRepository matchRepository;

    @BeforeEach
    void setUp() {
        matchRepository.deleteAll();
    }

    @Test
    public void getUserResult_정상_응답() {
        // given
        String groupId = "123abcABC";
        String giver = "giverName";
        String password = "password";
        MatchGetResultReq req = MatchDtoMother.createMatchGetResultReq(password);

        String receiver = "receiverName";
        MatchResult matchResult1 = MatchResult.create(giver, password, receiver);
        MatchResult matchResult2 = MatchResult.create(receiver, password, giver);
        Match match = Match.create(groupId, List.of(matchResult1, matchResult2));
        matchRepository.save(match);

        // when
        MatchGetResultRes result = matchService.getUserResult(groupId, giver, req);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getReceiver()).isEqualTo(receiver);
    }

    @Test
    public void getUserResult_존재하지_않는_매치_조회() {
        // given
        String groupId = "123abcABC";
        String giver = "giverName";
        String password = "password";
        MatchGetResultReq req = MatchDtoMother.createMatchGetResultReq(password);

        // when

        // then
        assertThatThrownBy(() -> matchService.getUserResult(groupId, giver, req))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_NOT_FOUND);
    }

    @Test
    public void getUserResult_하나의_그룹에_여러_매치_정보가_존재하면_에러_반환() {
        // given
        String groupId = "123abcABC";
        String giver = "giverName";
        String password = "password";
        MatchGetResultReq req = MatchDtoMother.createMatchGetResultReq(password);

        String receiver = "receiverName";
        MatchResult matchResult1 = MatchResult.create(giver, password, receiver);
        MatchResult matchResult2 = MatchResult.create(receiver, password, giver);
        Match match = Match.create(groupId, List.of(matchResult1, matchResult2));
        Match duplicatedMatch = Match.create(groupId, List.of(matchResult1, matchResult2));
        matchRepository.save(match);
        matchRepository.save(duplicatedMatch);

        // when

        // then
        assertThatThrownBy(() -> matchService.getUserResult(groupId, giver, req))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_INTEGRITY_VIOLATION);
    }

    @Test
    public void getUserResult_그룹_내_동일한_매치정보가_있는_경우_에러_반환() {
        // given
        String groupId = "123abcABC";
        String giver = "giverName";
        String password = "password";
        MatchGetResultReq req = MatchDtoMother.createMatchGetResultReq(password);

        String receiver = "receiverName";
        MatchResult matchResult = MatchResult.create(giver, password, receiver);
        MatchResult duplicatedMatchResult = MatchResult.create(giver, password, receiver);
        Match match = Match.create(groupId, List.of(matchResult, duplicatedMatchResult));
        matchRepository.save(match);

        // when

        // then
        assertThatThrownBy(() -> matchService.getUserResult(groupId, giver, req))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_INTEGRITY_VIOLATION);
    }
}