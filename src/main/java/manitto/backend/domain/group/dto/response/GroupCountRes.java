package manitto.backend.domain.group.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupCountRes {

    @Schema(description = "누적 생성된 그룹 개수")
    private int count;
}
