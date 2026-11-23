package manitto.backend.global.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringProcessor {

    // 유니코드 공백(전각 공백 포함)과 제어 문자. trim()은 전각 공백을, strip()은 제어 문자를 지우지 못한다.
    private static final Pattern EDGE_BLANK = Pattern.compile(
            "^[\\p{IsWhite_Space}\\p{Cntrl}]+|[\\p{IsWhite_Space}\\p{Cntrl}]+$");

    /**
     * 앞뒤의 공백과 제어 문자를 제거한다. 검사·저장에 같은 값을 쓰도록 이름 정규화는 이 메서드로 통일한다.
     */
    public static String normalize(String s) {
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
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }

    public static boolean isMeaningful(String s) {
        if (s == null) {
            return false;
        }
        String cleaned = s.replaceAll("[\\p{Space}\\p{Cntrl}]", "");
        return !cleaned.isEmpty();
    }
}
