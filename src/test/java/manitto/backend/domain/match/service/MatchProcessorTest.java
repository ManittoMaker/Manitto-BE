package manitto.backend.domain.match.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import manitto.backend.domain.match.entity.MatchResult;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class MatchProcessorTest {

    private final Random random = new Random(31); // 테스트를 위한 고정 시드값
    private final MatchProcessor processor = new MatchProcessor(random);

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

    @Test
    void matching_순서가_제대로_섞여서_랜덤한_매칭_값이_나온다() {
        // given
        List<String> names = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        // 7 -> 1 -> 2 -> 6 -> 8 -> 3 -> 5 -> 9 -> 4 -> 7

        // when
        List<MatchResult> result = processor.matching(names);

        // then
        assertThat(result.get(0).getGiver()).isEqualTo("7");
        assertThat(result.get(0).getReceiver()).isEqualTo("1");
        assertThat(result.get(1).getReceiver()).isEqualTo("2");
        assertThat(result.get(2).getReceiver()).isEqualTo("6");
        assertThat(result.get(3).getReceiver()).isEqualTo("8");
        assertThat(result.get(4).getReceiver()).isEqualTo("3");
        assertThat(result.get(5).getReceiver()).isEqualTo("5");
        assertThat(result.get(6).getReceiver()).isEqualTo("9");
        assertThat(result.get(7).getReceiver()).isEqualTo("4");
        assertThat(result.get(8).getReceiver()).isEqualTo("7");
    }

    @Test
    void matching_이_순열_랜덤으로_겹치거나_빠지는_사람이_없다() {
        // given
        List<String> names = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        // 7 -> 1 -> 2 -> 6 -> 8 -> 3 -> 5 -> 9 -> 4 -> 7

        // when
        List<MatchResult> result = processor.matching(names);

        // then
        assertThat(result.get(0).getReceiver()).isEqualTo(result.get(1).getGiver());
        assertThat(result.get(1).getReceiver()).isEqualTo(result.get(2).getGiver());
        assertThat(result.get(2).getReceiver()).isEqualTo(result.get(3).getGiver());
        assertThat(result.get(3).getReceiver()).isEqualTo(result.get(4).getGiver());
        assertThat(result.get(4).getReceiver()).isEqualTo(result.get(5).getGiver());
        assertThat(result.get(5).getReceiver()).isEqualTo(result.get(6).getGiver());
        assertThat(result.get(6).getReceiver()).isEqualTo(result.get(7).getGiver());
        assertThat(result.get(7).getReceiver()).isEqualTo(result.get(8).getGiver());
        assertThat(result.get(8).getReceiver()).isEqualTo(result.get(0).getGiver());
    }
}