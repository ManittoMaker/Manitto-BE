package manitto.backend.domain.group.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

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
    void validateLeaderAndGroupUnique_정상_입력한_리더_이름과_그룹_이름이_유일함() {
        // given
        String leaderName = "새로운리더";
        String groupName = "새로운그룹";

        when(groupRepository.existsByLeaderNameAndGroupName(leaderName, groupName))
                .thenReturn(false);

        // when

        // then
        assertThatCode(() -> groupValidator.validateLeaderAndGroupUnique(leaderName, groupName))
                .doesNotThrowAnyException();
    }

    @Test
    void validateLeaderAndGroupUnique_에러_입력한_리더_이름과_그룹_이름이_유일하지_않음() {
        // given
        String leaderName = "이미존재하는리더";
        String groupName = "이미존재하는그룹";

        when(groupRepository.existsByLeaderNameAndGroupName(leaderName, groupName))
                .thenReturn(true);

        // when

        // then
        assertThatThrownBy(() -> groupValidator.validateLeaderAndGroupUnique(leaderName, groupName))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.GROUP_LEADER_AND_GROUP_NAME_DUPLICATED);
    }
}