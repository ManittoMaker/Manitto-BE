package manitto.backend.domain.match.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import manitto.backend.domain.match.entity.MatchResult;
import org.springframework.stereotype.Component;

@Component
public class MatchProcessor {

    public List<MatchResult> matching(List<String> names) {
        List<String> mutableList = new ArrayList<>(names);
        Collections.shuffle(mutableList);

        return IntStream.range(0, names.size())
                .mapToObj(i -> MatchResult.create(
                        names.get(i),
                        "password",
                        names.get((i + 1) % names.size())
                ))
                .toList();
    }
}
