package manitto.backend.domain.match.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchStartReq {

    @NotNull
    @Size(min = 2, message = "요소 개수는 최소 2개 이상이어야 합니다.")
    private List<String> names;
}
