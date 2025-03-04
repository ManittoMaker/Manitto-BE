package manitto.backend.domain.group.service;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.dto.GroupDtoMapper.GroupDtoMapper;
import manitto.backend.domain.group.dto.request.GroupCreateReq;
import manitto.backend.domain.group.dto.response.GroupCreateRes;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.global.util.PasswordProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupCreateRes create(GroupCreateReq req) {

        String leaderName = req.getLeaderName();
        String groupName = req.getGroupName();
        String password = PasswordProvider.generatePassword();

        Group group = Group.create(leaderName, groupName, password);
        group = groupRepository.save(group);

        return GroupDtoMapper.toGroupCreateRes(group.getId());
    }

}
