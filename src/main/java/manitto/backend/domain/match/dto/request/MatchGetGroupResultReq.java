package manitto.backend.domain.match.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import manitto.backend.global.util.StringProcessor;

@Getter
@Setter
public class MatchGetGroupResultReq {

    @NotBlank
    private String leaderName;

    @NotBlank
    private String groupName;

    @NotBlank
    private String password;

    public void setLeaderName(String leaderName) {
        this.leaderName = StringProcessor.stripBlank(leaderName);
    }

    public void setGroupName(String groupName) {
        this.groupName = StringProcessor.stripBlank(groupName);
    }

    public void setPassword(String password) {
        this.password = StringProcessor.stripBlank(password);
    }
}
