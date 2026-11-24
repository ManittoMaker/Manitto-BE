package manitto.backend.domain.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import manitto.backend.global.util.StringProcessor;

@Getter
@Setter
public class GroupCreateReq {

    @NotBlank
    private String groupName;

    @NotBlank
    private String leaderName;

    public void setGroupName(String groupName) {
        this.groupName = StringProcessor.stripBlank(groupName);
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = StringProcessor.stripBlank(leaderName);
    }
}