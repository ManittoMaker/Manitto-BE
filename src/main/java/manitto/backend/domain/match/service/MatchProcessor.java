package manitto.backend.domain.match.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.global.util.PasswordProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchProcessor {

    private final Random random;

    public List<MatchResult> matching(List<String> names) {
        List<String> mutableList = new ArrayList<>(names);
        Collections.shuffle(mutableList, random);

        return IntStream.range(0, names.size())
                .mapToObj(i -> MatchResult.create(
                        mutableList.get(i),
                        PasswordProvider.generatePassword(),
                        mutableList.get((i + 1) % mutableList.size())
                ))
                .toList();
    }
}
