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

    // 요청을 받는 시점에 정규화해, 중복 검사와 저장이 같은 값을 사용하게 한다
    public void setGroupName(String groupName) {
        this.groupName = StringProcessor.normalize(groupName);
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = StringProcessor.normalize(leaderName);
    }
}