package manitto.backend.global.config.swagger;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import manitto.backend.global.exception.ErrorCode;

@Getter
public enum SwaggerResponseDescription {

    MATCH_GET_USER_RESULT(new LinkedHashSet<>(Set.of(
            ErrorCode.MATCH_INTEGRITY_VIOLATION
    ))),
    MATCH_START(new LinkedHashSet<>(Set.of(
            ErrorCode.GROUP_NOT_FOUND,
            ErrorCode.MATCH_MEMBER_NAME_DUPLICATED,
            ErrorCode.MATCH_ALREADY_EXIST
    ))),
    MATCH_GET_FINAL_RESULT(new LinkedHashSet<>(Set.of(
            ErrorCode.GROUP_NOT_FOUND,
            ErrorCode.MATCH_NOT_FOUND,
            ErrorCode.MATCH_INTEGRITY_VIOLATION
    ))),
    MATCH_GET_GROUP_RESULT(new LinkedHashSet<>(Set.of(
            ErrorCode.GROUP_NOT_FOUND,
            ErrorCode.MATCH_NOT_FOUND
    ))),
    GROUP_CREATE(new LinkedHashSet<>(Set.of(
            ErrorCode.GROUP_LEADER_AND_GROUP_NAME_DUPLICATED
    ))),
    GROUP_GET_NAME(new LinkedHashSet<>(Set.of(

    )));

    private Set<ErrorCode> errorCodeList;

    SwaggerResponseDescription(Set<ErrorCode> errorCodeList) {
        // 공통 에러
        errorCodeList.addAll(new LinkedHashSet<>(Set.of(
                ErrorCode.SERVER_UNTRACKED_ERROR,
                ErrorCode.INVALID_PARAMETER,
                ErrorCode.PARAMETER_VALIDATION_ERROR,
                ErrorCode.PARAMETER_GRAMMAR_ERROR,
                ErrorCode.OBJECT_NOT_FOUND
        )));

        if (this.name().startsWith("MATCH_")) {
            errorCodeList.add(ErrorCode.MATCH_NOT_FOUND);
        }

        if (this.name().startsWith("GROUP_")) {
            errorCodeList.add(ErrorCode.GROUP_NOT_FOUND);
        }

        this.errorCodeList = errorCodeList;
    }
}
