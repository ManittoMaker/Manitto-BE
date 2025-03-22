package manitto.backend.domain.group.service;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.dto.mapper.GroupDtoMapper;
import manitto.backend.domain.group.dto.request.GroupCreateReq;
import manitto.backend.domain.group.dto.response.GroupCountRes;
import manitto.backend.domain.group.dto.response.GroupCreateRes;
import manitto.backend.domain.group.dto.response.GroupGetNameRes;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupCountTemplateRepository;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.global.repository.GlobalMongoTemplateRepository;
import manitto.backend.global.util.PasswordProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupValidator groupValidator;
    private final GroupRepository groupRepository;
    private final GroupCountTemplateRepository groupCountTemplateRepository;
    private final GlobalMongoTemplateRepository globalMongoTemplateRepository;

    public GroupCreateRes create(GroupCreateReq req) {

        groupValidator.validateLeaderAndGroupUnique(req.getLeaderName(), req.getGroupName());

        Group group = Group.create(req.getLeaderName(), req.getGroupName(), PasswordProvider.generatePassword());
        globalMongoTemplateRepository.saveWithoutDuplicatedId(group, Group.class);
        groupCountTemplateRepository.updateTotalGroups();

        return GroupDtoMapper.toGroupCreateRes(group.getId(), group.getLeaderName(), group.getGroupName(),
                group.getPassword());
    }

    public GroupCountRes count() {
        int count = groupCountTemplateRepository.getTotalGroups();
        return GroupDtoMapper.toGroupCountRes(count);
    }

    public GroupGetNameRes getGroupName(String groupId) {
        return null;
    }
}
