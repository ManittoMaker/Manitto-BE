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
}