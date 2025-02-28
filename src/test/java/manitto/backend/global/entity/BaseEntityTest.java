package manitto.backend.global.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BaseEntityTest {

    @Test
    void generateFirestoreId_형식에_맞는_랜덤_아이디를_생성한다() {
        // given
        String correctPattern = "^[0-9a-f]{20}$"; // 숫자와 소문자로만 이루어진 20자리 문자열

        // when
        String generatedId = BaseEntity.generateFirestoreId();

        // then
        assertThat(generatedId).matches(correctPattern);
    }
}