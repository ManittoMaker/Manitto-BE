package manitto.backend.domain.match.dto.mapper;

import java.util.List;
import manitto.backend.domain.match.dto.response.MatchGetFinalResultRes;
import manitto.backend.domain.match.dto.response.MatchGetGroupResultRes;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.entity.MatchResult;

public class MatchDtoMapper {

    public static MatchGetResultRes toMatchGetResultRes(String receiver) {
        return MatchGetResultRes.builder()
                .receiver(receiver)
                .build();
    }

    public static MatchGetGroupResultRes toMatchGetGroupResultRes(List<MatchResult> result) {
        return MatchGetGroupResultRes.builder()
                .result(result)
                .build();
    }

    public static MatchGetFinalResultRes toMatchGetFinalResultRes(String leaderName, String groupName,
                                                                  List<MatchResult> result) {
        return MatchGetFinalResultRes.builder()
                .leaderName(leaderName)
                .groupName(groupName)
                .result(result)
                .build();
    }
}
