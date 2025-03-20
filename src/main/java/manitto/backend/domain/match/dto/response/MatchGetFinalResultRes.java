package manitto.backend.domain.match.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import manitto.backend.domain.match.entity.MatchResult;

@Getter
@Builder
public class MatchGetFinalResultRes {

    @Schema(description = "리더 이름")
    private String leaderName;

    @Schema(description = "그룹 이름")
    private String groupName;

    @Schema(description = "매칭 중 전체 매칭 결과")
    private List<MatchResult> result;
}
