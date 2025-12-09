package manitto.backend.global.util;

import java.util.List;
import java.util.stream.Collectors;

public class StringListProcessor {

    private StringListProcessor() {
    }

    public static List<String> filterNotBlank(List<String> list) {

        return list.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
