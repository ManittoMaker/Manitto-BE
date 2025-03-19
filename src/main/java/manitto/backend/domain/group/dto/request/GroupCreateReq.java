package manitto.backend.domain.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupCreateReq {

    @NotBlank
    private String groupName;

    @NotBlank
    private String leaderName;
}