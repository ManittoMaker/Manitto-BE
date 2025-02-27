package manitto.backend.domain.group.service;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupValidator {

    private final GroupRepository groupRepository;

    public void validateExists(String groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        }
    }
}
