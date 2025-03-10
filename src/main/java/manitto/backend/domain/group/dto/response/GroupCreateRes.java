package manitto.backend.domain.group.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupCreateRes {

    @Schema(description = "생성된 그룹 ID")
    private String groupId;
}
