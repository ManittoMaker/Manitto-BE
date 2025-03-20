package manitto.backend.domain.match.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchGetGroupResultReq {

    @NotBlank
    private String leaderName;

    @NotBlank
    private String groupName;

    @NotBlank
    private String password;
}
