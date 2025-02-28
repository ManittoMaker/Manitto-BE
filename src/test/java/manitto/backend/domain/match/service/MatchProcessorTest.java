package manitto.backend.domain.match.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import manitto.backend.domain.match.entity.MatchResult;
import org.junit.jupiter.api.Test;

class MatchProcessorTest {

    private final MatchProcessor processor = new MatchProcessor();

    @Test
    void matching_리스트_개수가_1개여도_성공한다() {
        // given
        List<String> names = List.of("one");

        // when
        List<MatchResult> result = processor.matching(names);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void matching_리스트_개수가_2개면_서로에_대해_매칭된다() {
        // given
        String member1 = "name1";
        String member2 = "name2";
        List<String> names = List.of(member1, member2);

        // when
        List<MatchResult> result = processor.matching(names);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

        Map<String, String> matchMap = result.stream()
                .collect(Collectors.toMap(MatchResult::getGiver, MatchResult::getReceiver));
        assertThat(matchMap).containsEntry(member1, member2);
        assertThat(matchMap).containsEntry(member2, member1);
    }

    @Test
    void matching_리스트_개수가_몇개라도_정상적으로_매칭된다() {
        // given
        List<String> names = new Random().ints(1, 500)
                .limit(1)
                .flatMap(n -> IntStream.range(0, n))
                .mapToObj(i -> "name" + i)
                .toList();

        // when
        List<MatchResult> result = processor.matching(names);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(names.size());
    }
}