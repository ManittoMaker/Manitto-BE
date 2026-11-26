package manitto.backend.domain.match.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import manitto.backend.config.mongo.EnableMongoTestServer;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@EnableMongoTestServer
class MatchControllerTest {

    private static final String LEADER_NAME = "팽도리";
    private static final String GROUP_NAME = "동물";
    private static final String GROUP_PASSWORD = "빨강수달";
    private static final String GIVER = "철수";
    private static final String GIVER_PASSWORD = "파란산양";
    private static final String RECEIVER = "영희";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MatchRepository matchRepository;

    private String groupId;

    @BeforeEach
    void setUp() {
        groupRepository.deleteAll();
        matchRepository.deleteAll();
        groupId = saveGroupWithMatch(GROUP_PASSWORD);
    }

    @Test
    void getGroupResult_에러_리더명과_그룹명_조합으로_생성된_그룹이_없으면_그룹_없음() throws Exception {
        assertError(getGroupResult(LEADER_NAME, "없는 그룹", GROUP_PASSWORD), ErrorCode.GROUP_NOT_FOUND);
    }

    @Test
    void getGroupResult_에러_리더명과_그룹명은_일치하고_비밀번호만_다르면_비밀번호_불일치() throws Exception {
        assertError(getGroupResult(LEADER_NAME, GROUP_NAME, "틀린비밀번호"), ErrorCode.GROUP_PASSWORD_MISMATCH);
    }

    @Test
    void getGroupResult_에러_그룹은_있지만_매칭이_없으면_매칭_없음() throws Exception {
        groupRepository.save(Group.create(LEADER_NAME, "매칭 안 한 그룹", GROUP_PASSWORD));

        assertError(getGroupResult(LEADER_NAME, "매칭 안 한 그룹", GROUP_PASSWORD), ErrorCode.MATCH_NOT_FOUND);
    }

    @Test
    void getGroupResult_정상_리더명과_그룹명이_같은_그룹이_여러_개면_비밀번호가_일치하는_그룹을_조회한다() throws Exception {
        String otherGroupId = saveGroupWithMatch("노랑삵");

        getGroupResult(LEADER_NAME, GROUP_NAME, "노랑삵")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.groupId").value(otherGroupId));
    }

    @Test
    void getUserResult_에러_매칭에_해당_이름이_없으면_참여자_없음() throws Exception {
        assertError(getUserResult(groupId, "없는 사람", GIVER_PASSWORD), ErrorCode.MATCH_MEMBER_NOT_FOUND);
    }

    @Test
    void getUserResult_에러_이름은_있고_비밀번호만_다르면_비밀번호_불일치() throws Exception {
        assertError(getUserResult(groupId, GIVER, "틀린비밀번호"), ErrorCode.MATCH_PASSWORD_MISMATCH);
    }

    @Test
    void getUserResult_에러_그룹에_매칭이_없으면_매칭_없음() throws Exception {
        assertError(getUserResult("notExistGroupId", GIVER, GIVER_PASSWORD), ErrorCode.MATCH_NOT_FOUND);
    }

    private String saveGroupWithMatch(String groupPassword) {
        Group group = groupRepository.save(Group.create(LEADER_NAME, GROUP_NAME, groupPassword));
        matchRepository.save(Match.create(group.getId(), List.of(
                MatchResult.create(GIVER, GIVER_PASSWORD, RECEIVER),
                MatchResult.create(RECEIVER, "초록노루", GIVER)
        )));
        return group.getId();
    }

    private ResultActions getGroupResult(String leaderName, String groupName, String password) throws Exception {
        return mockMvc.perform(get("/api/matches/results")
                .param("leaderName", leaderName)
                .param("groupName", groupName)
                .param("password", password));
    }

    private ResultActions getUserResult(String groupId, String name, String password) throws Exception {
        return mockMvc.perform(get("/api/matches/{groupId}/result/user/{name}", groupId, name)
                .param("password", password));
    }

    private void assertError(ResultActions result, ErrorCode errorCode) throws Exception {
        result.andExpect(status().is(errorCode.getHttpCode()))
                .andExpect(jsonPath("$.code").value(errorCode.getErrorCode()));
    }
}
