package manitto.backend.domain.group.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupGetNameRes {

    private String groupName;
}
