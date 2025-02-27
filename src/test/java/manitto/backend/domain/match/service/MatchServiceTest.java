package manitto.backend.domain.match.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import manitto.backend.config.mongo.EnableMongoTestServer;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.request.MatchStartReq;
import manitto.backend.domain.match.dto.response.MatchAllResultRes;
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
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableMongoTestServer
class MatchServiceTest {

    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        groupRepository.deleteAll();
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
    @Test
    void matchStart_정상_응답_멤버가_2명() {
        // given
        String leaderName = "leader";
        String groupName = "group";
        String password = "password";

        String member1 = "name1";
        String member2 = "name2";
        MatchStartReq req = MatchDtoMother.createMatchStartReq(List.of(member1, member2));

        Group group = Group.create(leaderName, groupName, password);
        group = groupRepository.save(group);

        // when
        MatchAllResultRes result = matchService.matchStart(group.getId(), req);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getGroupId()).isEqualTo(group.getId());
        assertThat(result.getResult())
                .extracting(MatchResult::getPassword)
                .doesNotContainNull();

        Map<String, String> matchMap = result.getResult().stream()
                .collect(Collectors.toMap(MatchResult::getGiver, MatchResult::getReceiver));
        assertThat(matchMap).containsEntry(member1, member2);
        assertThat(matchMap).containsEntry(member2, member1);
    }

    @Test
    void matchStart_중복된_멤버_이름_존재() {
        // given
        String leaderName = "leader";
        String groupName = "group";
        String password = "password";

        String member1 = "sameName";
        String member2 = "sameName";
        MatchStartReq req = MatchDtoMother.createMatchStartReq(List.of(member1, member2));

        Group group = Group.create(leaderName, groupName, password);
        groupRepository.save(group);

        // when

        // then
        assertThatThrownBy(() -> matchService.matchStart(group.getId(), req))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.MATCH_MEMBER_NAME_DUPLICATED);
    }
}