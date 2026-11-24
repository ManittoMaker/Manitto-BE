package manitto.backend.global.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class StringProcessorTest {

    @Test
    void filterNotBlank_정상() {
        // given
        List<String> list = List.of("a", "b\t", "\n\b");

        // when
        List<String> result = StringProcessor.filterNotBlank(list);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly("a", "b");
    }

    @Test
    void filterNotBlank_정상_입력값_null() {
        // given
        List<String> list = null;

        // when
        List<String> result = StringProcessor.filterNotBlank(list);

        // then
        assertThat(result).hasSize(0);
    }

    @Test
    void filterNotBlank_정상_입력값_empty() {
        // given
        List<String> list = List.of();

        // when
        List<String> result = StringProcessor.filterNotBlank(list);

        // then
        assertThat(result).hasSize(0);
    }

    @Test
    void isMeaningful_true() {
        assertThat(StringProcessor.isMeaningful("a")).isTrue();
        assertThat(StringProcessor.isMeaningful("  a  ")).isTrue();
        assertThat(StringProcessor.isMeaningful("a\b")).isTrue();   // 제어문자 제거 후 'a'
        assertThat(StringProcessor.isMeaningful("\t x \b")).isTrue();
        assertThat(StringProcessor.isMeaningful("1")).isTrue();
        assertThat(StringProcessor.isMeaningful("!@#")).isTrue();
    }

    @Test
    void isMeaningful_false() {
        String[] invalidCases = {
                null,
                "",
                "   ",
                "\t",
                "\n",
                "\r\n",
                "\b",
                "\u0007",
                "\u0000",
                "\t\b",
                " \n\b\t",
                "\r\b "
        };

        for (String input : invalidCases) {
            assertThat(StringProcessor.isMeaningful(input))
                    .isFalse();
        }
    }

    @Test
    void stripBlank_앞뒤_공백과_제어_문자를_제거하고_가운데는_유지한다() {
        assertThat(StringProcessor.stripBlank(" 동물 ")).isEqualTo("동물");
        assertThat(StringProcessor.stripBlank("\t동물\n")).isEqualTo("동물");
        assertThat(StringProcessor.stripBlank("동물\u3000")).isEqualTo("동물");
        assertThat(StringProcessor.stripBlank("\b동물")).isEqualTo("동물");
        assertThat(StringProcessor.stripBlank(" 우리 반 ")).isEqualTo("우리 반");
        assertThat(StringProcessor.stripBlank(null)).isNull();
    }

    @Test
    void filterNotBlank_전각_공백과_제어_문자도_앞뒤에서_제거하고_빈_값은_거른다() {
        List<String> list = List.of("철수\u3000", "\b영희", "\u3000");

        assertThat(StringProcessor.filterNotBlank(list)).containsExactly("철수", "영희");
    }
}