package manitto.backend.domain.match.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchValidator {

    private final MatchRepository matchRepository;

    public void validateAlreadyExists(String groupId) {
        if (matchRepository.existsByGroupId(groupId)) {
            throw new CustomException(ErrorCode.MATCH_ALREADY_EXIST);
        }
    }

    public void validateDuplicateName(List<String> names) {
        Set<String> uniqueNames = names.stream()
                .map(String::trim)
                .collect(Collectors.toSet());

        if (names.size() != uniqueNames.size()) {
            throw new CustomException(ErrorCode.MATCH_MEMBER_NAME_DUPLICATED);
        }
    }

    public void validateMinimumSize(List<String> names) {
        if (names.size() < 2) {
            throw new CustomException(ErrorCode.MATCH_MEMBER_SIZE_TOO_SMALL);
        }
    }
}
