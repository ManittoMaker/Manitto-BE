package manitto.backend.domain.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import manitto.backend.config.mongo.EnableMongoTestServer;
import manitto.backend.domain.group.dto.request.GroupCreateReq;
import manitto.backend.domain.group.dto.response.GroupCountRes;
import manitto.backend.domain.group.dto.response.GroupCreateRes;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupCountTemplateRepository;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.global.exception.CustomException;
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
    void create_정상_그룹을_생성하면_groupCount가_비동기로_1_증가한다() {
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

        await().atMost(1, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(groupCountTemplateRepository.getTotalGroups())
                        .isEqualTo(beforeCreate + 1));
    }

    @Test
    void create_실패_그룹_생성이_실패하면_groupCount가_증가하지_않는다() throws InterruptedException {
        // given: 첫 번째 그룹 생성 성공 및 비동기 카운트 증가 대기
        GroupCreateReq req = GroupDtoMother.createGroupCreateReq("포켓몬", "팽도리");
        int countBefore = groupCountTemplateRepository.getTotalGroups();

        groupService.create(req);
        await().atMost(1, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(groupCountTemplateRepository.getTotalGroups())
                        .isEqualTo(countBefore + 1));

        int countBeforeFailure = groupCountTemplateRepository.getTotalGroups();

        // when: 동일한 리더 이름 + 그룹 이름으로 중복 생성 시도 → 예외 발생
        assertThatThrownBy(() -> groupService.create(req))
                .isInstanceOf(CustomException.class);

        // then: 비동기 작업이 실행될 여지(500ms)를 줘도 카운트가 변하지 않아야 함
        Thread.sleep(500);
        assertThat(groupCountTemplateRepository.getTotalGroups()).isEqualTo(countBeforeFailure);
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
