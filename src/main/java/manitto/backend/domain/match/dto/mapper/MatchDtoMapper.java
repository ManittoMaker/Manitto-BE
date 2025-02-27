package manitto.backend.domain.match.dto.mapper;

import java.util.List;
import manitto.backend.domain.match.dto.response.MatchAllResultRes;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.entity.MatchResult;

public class MatchDtoMapper {

    public static MatchGetResultRes toMatchGetResultRes(String receiver) {
        return MatchGetResultRes.builder()
                .receiver(receiver)
                .build();
    }

    public static MatchAllResultRes toMatchAllResultRes(String groupId, List<MatchResult> result) {
        return MatchAllResultRes.builder()
                .groupId(groupId)
                .result(result)
                .build();
    }
}
