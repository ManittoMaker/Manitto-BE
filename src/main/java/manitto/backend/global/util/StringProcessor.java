package manitto.backend.global.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringProcessor {

    private static final Pattern EDGE_BLANK = Pattern.compile(
            "^[\\p{IsWhite_Space}\\p{Cntrl}]+|[\\p{IsWhite_Space}\\p{Cntrl}]+$");

    /**
     * 앞뒤의 공백과 제어 문자를 제거한다.
     */
    public static String stripBlank(String s) {
        if (s == null) {
            return null;
        }
        return EDGE_BLANK.matcher(s).replaceAll("");
    }

    public static List<String> filterNotBlank(List<String> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        return list.stream()
                .filter(Objects::nonNull)
                .map(StringProcessor::stripBlank)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public static boolean isMeaningful(String s) {
        if (s == null) {
            return false;
        }
        return !stripBlank(s).isEmpty();
    }
}
