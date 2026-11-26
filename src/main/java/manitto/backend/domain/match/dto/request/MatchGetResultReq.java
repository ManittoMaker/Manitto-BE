package manitto.backend.domain.match.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import manitto.backend.global.util.StringProcessor;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@ParameterObject
public class MatchGetResultReq {

    @NotBlank
    private String password;

    public void setPassword(String password) {
        this.password = StringProcessor.stripBlank(password);
    }
}
