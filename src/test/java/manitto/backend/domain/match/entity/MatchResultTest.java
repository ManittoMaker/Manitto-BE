package manitto.backend.domain.match.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
}