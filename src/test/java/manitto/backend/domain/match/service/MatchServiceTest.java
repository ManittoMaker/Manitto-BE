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

    @Test
    public void getUserResult_정상_응답() {
        // given
        String groupId = "123abcABC";
        String giver = "giverName";
        String password = "password";
        MatchGetResultReq req = MatchDtoMother.createMatchGetResultReq(password);

        String receiver = "receiverName";
        MatchResult match1 = MatchResult.create(giver, password, receiver);
        MatchResult match2 = MatchResult.create(receiver, password, giver);
        Match match = Match.create(groupId, List.of(match1, match2));
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

        matchRepository.deleteAll();

        // when

        // then
        assertThatThrownBy(() -> matchService.getUserResult(groupId, giver, req))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_NOT_FOUND);
    }
}