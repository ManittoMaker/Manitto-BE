package manitto.backend.domain.group.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import manitto.backend.config.mongo.EnableMongoTestServer;
import manitto.backend.domain.group.dto.request.GroupCreateReq;
import manitto.backend.domain.group.dto.response.GroupCountRes;
import manitto.backend.domain.group.dto.response.GroupCreateRes;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupCountTemplateRepository;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.testUtil.GroupDtoMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("integration")
@SpringBootTest
@EnableMongoTestServer
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupCountTemplateRepository groupCountTemplateRepository;

    @BeforeEach
    void setUp() {
        groupRepository.deleteAll();
    }

    @Test
    void create_정상_그룹을_생성하면_생성된_그룹_id가_반환된다() {
        //given
        String groupName = "포켓몬";
        String leaderName = "팽도리";
        GroupCreateReq req = GroupDtoMother.createGroupCreateReq(groupName, leaderName);

        // when
        GroupCreateRes result = groupService.create(req);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getGroupId()).isNotBlank();

        Optional<Group> savedGroup = groupRepository.findById(result.getGroupId());
        assertThat(savedGroup).isPresent();
        assertThat(savedGroup.get().getGroupName()).isEqualTo("포켓몬");
        assertThat(savedGroup.get().getLeaderName()).isEqualTo("팽도리");
    }

    @Test
    void create_정상_그룹을_생성하면_groupCount가_1_증가한다() {
        //given
        String groupName = "포켓몬";
        String leaderName = "팽도리";
        GroupCreateReq req = GroupDtoMother.createGroupCreateReq(groupName, leaderName);

        int beforeCreate = groupCountTemplateRepository.getTotalGroups();

        // when
        GroupCreateRes result = groupService.create(req);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getGroupId()).isNotBlank();

        assertThat(groupCountTemplateRepository.getTotalGroups()).isEqualTo(beforeCreate + 1);
    }

    @Test
    void count_정상응답() {
        // given

        // when
        GroupCountRes result = groupService.count();

        // then
        assertThat(result.getCount()).isInstanceOf(Integer.class);
    }
}
