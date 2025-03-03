package manitto.backend.domain.group.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupCreateReq {

    @NotNull
    private String groupName;

    @NotNull
    private String leaderName;
}