package manitto.backend.domain.match.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class MatchValidator {

    public void validateDuplicateName(List<String> names) {
        Set<String> uniqueNames = new HashSet<>(names);
        if (names.size() != uniqueNames.size()) {
            throw new CustomException(ErrorCode.MATCH_MEMBER_NAME_DUPLICATED);
        }
    }
}
