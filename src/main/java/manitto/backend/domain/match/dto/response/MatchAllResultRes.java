package manitto.backend.domain.match.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import manitto.backend.domain.match.entity.MatchResult;

@Getter
@Builder
public class MatchAllResultRes {

    @Schema(description = "그룹 id")
    private String groupId;

    @Schema(description = "매칭 결과")
    private List<MatchResult> result;
}
