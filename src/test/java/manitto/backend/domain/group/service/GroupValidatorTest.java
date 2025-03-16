package manitto.backend.domain.group.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class GroupValidatorTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupValidator groupValidator;


    @Test
    void validateExists_정상_존재하는_그룹() {
        // given
        String groupId = "groupId";

        // 존재함
        when(groupRepository.existsById(groupId))
                .thenReturn(true);

        // when

        // then
        assertThatCode(() -> groupValidator.validateExists(groupId))
                .doesNotThrowAnyException();
    }

    @Test
    void validateExists_에러_존재하지_않음() {
        // given
        String groupId = "notExistGroupId";

        // 존재함
        when(groupRepository.existsById(groupId))
                .thenReturn(false);

        // when

        // then
        assertThatThrownBy(() -> groupValidator.validateExists(groupId))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.GROUP_NOT_FOUND);
    }

    @Test
    void validateExistsByInfo_정상_특정_리더명_그룹명_비밀번호의_그룹이_존재함() {
        // given
        String leaderName = "leader";
        String groupName = "group";
        String password = "password";
        when(groupRepository.getGroupByLeaderNameAndGroupNameAndPassword(leaderName, groupName, password))
                .thenReturn(Group.create(leaderName, groupName, password));

        // when

        // then
        assertThatCode(() -> groupValidator.validateExistsByInfo(leaderName, groupName, password))
                .doesNotThrowAnyException();
    }

    @Test
    void validateExistsByInfo_에러_특정_리더명_그룹명_비밀번호의_그룹이_존재하지_않음() {
        // given
        String leaderName = "wrongLeader";
        String groupName = "wrongGroup";
        String password = "wrongPassword";
        when(groupRepository.getGroupByLeaderNameAndGroupNameAndPassword(leaderName, groupName, password))
                .thenReturn(null);

        // when

        // then
        assertThatThrownBy(() -> groupValidator.validateExistsByInfo(leaderName, groupName, password))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.GROUP_NOT_FOUND);
    }
}