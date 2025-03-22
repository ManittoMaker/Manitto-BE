package manitto.backend.domain.match.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import manitto.backend.config.mongo.EnableMongoTestServer;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.domain.match.dto.request.MatchGetGroupResultReq;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.request.MatchStartReq;
import manitto.backend.domain.match.dto.response.MatchAllResultRes;
import manitto.backend.domain.match.dto.response.MatchGetFinalResultRes;
import manitto.backend.domain.match.dto.response.MatchGetGroupResultRes;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.testUtil.MatchDtoMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("integration")
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
    void getGroupResult_정상_응답() {
        // given
        String leaderName = "리더명";
        String groupName = "그룹명";
        String password = "그룹 비밀번호";
        Group group = Group.create(leaderName, groupName, password);
        group = groupRepository.save(group);

        List<MatchResult> matches = new ArrayList<>(List.of(
                MatchResult.create("멤버1", "멤버1 비밀번호", "멤버2"),
                MatchResult.create("멤버2", "멤버2 비밀번호", "멤버3"),
                MatchResult.create("멤버3", "멤버3 비밀번호", "멤버1")
        ));
        Match match = Match.create(group.getId(), matches);
        match = matchRepository.save(match);

        MatchGetGroupResultReq req = MatchDtoMother.createMatchGetGroupResultReq(leaderName, groupName, password);

        // when
        MatchGetGroupResultRes result = matchService.getGroupResult(req);

        // then
        assertThat(result.getResult()).isNotNull();

        List<MatchResult> matchesResult = result.getResult();

        for (int i = 0; i < matchesResult.size(); i++) {
            assertThat(matchesResult.get(i).getGiver()).isEqualTo(matches.get(i).getGiver());
            assertThat(matchesResult.get(i).getPassword()).isEqualTo(matches.get(i).getPassword());
            assertThat(matchesResult.get(i).getReceiver()).isEqualTo(matches.get(i).getReceiver());
        }
    }

    @Test
    void getFinalResult_정상_응답() {
        // given
        String leaderName = "리더명";
        String groupName = "그룹명";
        String password = "그룹 비밀번호";
        Group group = Group.create(leaderName, groupName, password);
        group = groupRepository.save(group);

        List<MatchResult> matches = new ArrayList<>(List.of(
                MatchResult.create("멤버1", "멤버1 비밀번호", "멤버2"),
                MatchResult.create("멤버2", "멤버2 비밀번호", "멤버3"),
                MatchResult.create("멤버3", "멤버3 비밀번호", "멤버1")
        ));
        Match match = Match.create(group.getId(), matches);
        match = matchRepository.save(match);

        // when
        MatchGetFinalResultRes result = matchService.getFinalResult(group.getId());

        // then
        assertThat(result.getLeaderName()).isNotBlank();
        assertThat(result.getGroupName()).isNotBlank();
        assertThat(result.getResult()).isNotNull();

        List<MatchResult> matchesResult = result.getResult();

        for (int i = 0; i < matchesResult.size(); i++) {
            assertThat(matchesResult.get(i).getGiver()).isEqualTo(matches.get(i).getGiver());
            assertThat(matchesResult.get(i).getPassword()).isEqualTo(matches.get(i).getPassword());
            assertThat(matchesResult.get(i).getReceiver()).isEqualTo(matches.get(i).getReceiver());
        }
    }
}