package manitto.backend.global.dto.response.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ServerErrorData {

    private String errorClass;
    private String errorMessage;
}
