package manitto.backend.domain.match.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.domain.match.repository.MatchTemplateRepository;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import manitto.backend.testUtil.MatchDtoMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=4.0.2")
@Import({MatchService.class, MatchTemplateRepository.class})
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
    void getUserResult_정상_응답() {
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
    void getUserResult_존재하지_않는_매치_조회() {
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
    void getUserResult_하나의_그룹에_여러_매치_정보가_존재하면_에러_반환() {
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

    // TODO - 매치 정보 삽입하는 로직에서 매치 결과 중복 검증 필요 - giver, receiver
}