package manitto.backend.domain.match.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchGetResultRes {

    @Schema(description = "매칭 결과 대상자")
    private String receiver;
}
