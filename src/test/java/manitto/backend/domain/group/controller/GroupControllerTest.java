package manitto.backend.domain.group.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.stream.Stream;
import manitto.backend.config.mongo.EnableMongoTestServer;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@EnableMongoTestServer
class GroupControllerTest {

    private static final String LEADER_NAME = "팽도리";
    private static final String GROUP_NAME = "동물";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    void setUp() throws Exception {
        groupRepository.deleteAll();
        createGroup(LEADER_NAME, GROUP_NAME);
    }

    static Stream<Arguments> 앞뒤_공백만_다른_리더명과_그룹명() {
        return Stream.of(
                Arguments.of(LEADER_NAME, GROUP_NAME + " "),
                Arguments.of(LEADER_NAME, " " + GROUP_NAME),
                Arguments.of(LEADER_NAME + " ", GROUP_NAME),
                Arguments.of(" " + LEADER_NAME, " " + GROUP_NAME + " "),
                Arguments.of(LEADER_NAME, "\t" + GROUP_NAME + "\n"),
                Arguments.of(LEADER_NAME, GROUP_NAME + "　"), // 전각 공백
                Arguments.of(LEADER_NAME, "\b" + GROUP_NAME)       // 제어 문자
        );
    }

    @ParameterizedTest(name = "리더명=[{0}], 그룹명=[{1}]")
    @MethodSource("앞뒤_공백만_다른_리더명과_그룹명")
    void create_에러_앞뒤_공백만_다른_리더명과_그룹명은_중복으로_판단하고_저장하지_않는다(String leaderName, String groupName)
            throws Exception {
        // when, then
        createGroup(leaderName, groupName)
                .andExpect(status().is(ErrorCode.GROUP_LEADER_AND_GROUP_NAME_DUPLICATED.getHttpCode()))
                .andExpect(jsonPath("$.code").value(ErrorCode.GROUP_LEADER_AND_GROUP_NAME_DUPLICATED.getErrorCode()));

        assertThat(groupRepository.count()).isEqualTo(1);
    }

    private org.springframework.test.web.servlet.ResultActions createGroup(String leaderName, String groupName)
            throws Exception {
        String body = objectMapper.writeValueAsString(Map.of("leaderName", leaderName, "groupName", groupName));
        return mockMvc.perform(post("/api/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
    }
}
