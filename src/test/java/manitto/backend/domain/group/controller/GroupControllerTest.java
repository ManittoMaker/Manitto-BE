package manitto.backend.domain.group.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import tools.jackson.databind.ObjectMapper;
import java.util.Map;
import manitto.backend.config.mongo.EnableMongoTestServer;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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

    @ParameterizedTest
    @ValueSource(strings = {
            "   ",       // whitespace only
            "\t",        // tab
            "\n",        // newline
            "\b",        // control char
            "\u0007",    // bell
            " \n\b\t",   // whitespace + control
            "\u3000"     // full-width space
    })
    void create_에러_앞뒤_공백만_다른_리더명과_그룹명은_중복으로_판단하고_저장하지_않는다(String blank) throws Exception {
        assertDuplicated(LEADER_NAME, GROUP_NAME + blank);
        assertDuplicated(LEADER_NAME, blank + GROUP_NAME);
        assertDuplicated(LEADER_NAME + blank, GROUP_NAME);
    }

    private void assertDuplicated(String leaderName, String groupName) throws Exception {
        createGroup(leaderName, groupName)
                .andExpect(status().is(ErrorCode.GROUP_LEADER_AND_GROUP_NAME_DUPLICATED.getHttpCode()))
                .andExpect(jsonPath("$.code").value(ErrorCode.GROUP_LEADER_AND_GROUP_NAME_DUPLICATED.getErrorCode()));

        assertThat(groupRepository.count()).isEqualTo(1);
    }

    private ResultActions createGroup(String leaderName, String groupName) throws Exception {
        String body = objectMapper.writeValueAsString(Map.of("leaderName", leaderName, "groupName", groupName));
        return mockMvc.perform(post("/api/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
    }
}
