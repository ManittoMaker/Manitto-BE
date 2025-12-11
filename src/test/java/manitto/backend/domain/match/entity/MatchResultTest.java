package manitto.backend.domain.match.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import manitto.backend.domain.group.entity.Group;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("unit")
class MatchResultTest {

    @Test
    void create_내부_빌더를_통해_정상적으로_생성됨() {
        // given
        String giver = "giver";
        String password = "password";
        String receiver = "receiver";

        // when
        MatchResult matchResult = MatchResult.create(giver, password, receiver);

        // then
        assertThat(matchResult).isNotNull();
        assertThat(matchResult.getGiver()).isEqualTo(giver);
        assertThat(matchResult.getPassword()).isEqualTo(password);
        assertThat(matchResult.getReceiver()).isEqualTo(receiver);
    }

    @Test
    void create_앞뒤_공백은_제거됨() {
        // given
        String giver = "giver ";
        String password = "\t\b\rpassword";
        String receiver = " receiver \n ";

        // when
        MatchResult matchResult = MatchResult.create(giver, password, receiver);

        // then
        assertThat(matchResult).isNotNull();
        assertThat(matchResult.getGiver()).isEqualTo("giver");
        assertThat(matchResult.getPassword()).isEqualTo("password");
        assertThat(matchResult.getReceiver()).isEqualTo("receiver");
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
        assertThatThrownBy(() -> MatchResult.create(invalidInput, "Password", "Receiver"))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_PARAMETER);

        assertThatThrownBy(() -> Group.create("Giver", invalidInput, "Receiver"))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_PARAMETER);

        assertThatThrownBy(() -> Group.create("Giver", "Password", invalidInput))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_PARAMETER);
    }
}