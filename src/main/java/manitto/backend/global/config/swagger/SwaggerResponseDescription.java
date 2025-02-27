package manitto.backend.global.config.swagger;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import manitto.backend.global.exception.ErrorCode;

@Getter
public enum SwaggerResponseDescription {

    GET_USER_RESULT(new LinkedHashSet<>(Set.of(

    ))),
    MATCH_START(new LinkedHashSet<>(Set.of(

    )));

    private Set<ErrorCode> errorCodeList;

    SwaggerResponseDescription(Set<ErrorCode> errorCodeList) {
        // 공통 에러
        errorCodeList.addAll(new LinkedHashSet<>(Set.of(
                ErrorCode.SERVER_UNTRACKED_ERROR,
                ErrorCode.INVALID_PARAMETER,
                ErrorCode.PARAMETER_VALIDATION_ERROR,
                ErrorCode.PARAMETER_GRAMMAR_ERROR,
                ErrorCode.UNAUTHORIZED,
                ErrorCode.FORBIDDEN,
                ErrorCode.AUTHORIZED_ERROR,
                ErrorCode.OBJECT_NOT_FOUND
        )));

        this.errorCodeList = errorCodeList;
    }
}
