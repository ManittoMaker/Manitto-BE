package manitto.backend.domain.group.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("unit")
class GroupTest {

    @Test
    void generateNewId_새로운_아이디가_정상적으로_세팅됨() {
        // given
        String leaderName = "Leader Name";
        String groupName = "Group Name";
        String password = "Password";

        Group group = Group.create(leaderName, groupName, password);

        // when
        String beforeId = group.getId();
        group.generateNewId();
        String afterId = group.getId();

        // then
        assertThat(beforeId).isNotEqualTo(afterId);
    }

    @Test
    void create_내부_빌더를_통해_정상적으로_생성됨() {
        // given
        String leaderName = "Leader Name";
        String groupName = "Group Name";
        String password = "Password";

        // when
        Group group = Group.create(leaderName, groupName, password);

        // then
        assertThat(group).isNotNull();
        assertThat(group.getLeaderName()).isEqualTo(leaderName);
        assertThat(group.getGroupName()).isEqualTo(groupName);
        assertThat(group.getPassword()).isEqualTo(password);
        assertThat(group.getId()).isNotNull();
    }

    @Test
    void create_앞뒤_공백은_제거됨() {
        // given
        String leaderName = " Leader Name";
        String groupName = "Group Name\n";
        String password = "\tPassword  ";

        // when
        Group group = Group.create(leaderName, groupName, password);

        // then
        assertThat(group).isNotNull();
        assertThat(group.getLeaderName()).isEqualTo("Leader Name");
        assertThat(group.getGroupName()).isEqualTo("Group Name");
        assertThat(group.getPassword()).isEqualTo("Password");
        assertThat(group.getId()).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",          // empty
            "   ",       // whitespace only
            "\t",        // tab
            "\n",        // newline
            "\b",        // control char
            "\u0007",    // bell
            " \n\b\t"    // whitespace + control
    })
    void create_의미_없는_입력값(String invalidInput) {
        assertThatThrownBy(() -> Group.create(invalidInput, "Group", "Pass"))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_PARAMETER);

        assertThatThrownBy(() -> Group.create("Leader", invalidInput, "Pass"))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_PARAMETER);

        assertThatThrownBy(() -> Group.create("Leader", "Group", invalidInput))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_PARAMETER);
    }
}