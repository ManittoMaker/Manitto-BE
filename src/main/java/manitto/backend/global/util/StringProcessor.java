package manitto.backend.global.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringProcessor {

    public static List<String> filterNotBlank(List<String> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        return list.stream()
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
