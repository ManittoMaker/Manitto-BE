package manitto.backend.domain.group.service;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.dto.GroupDtoMapper.GroupDtoMapper;
import manitto.backend.domain.group.dto.request.GroupCreateReq;
import manitto.backend.domain.group.dto.response.GroupCountRes;
import manitto.backend.domain.group.dto.response.GroupCreateRes;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupCountTemplateRepository;
import manitto.backend.global.repository.GlobalMongoTemplateRepository;
import manitto.backend.global.util.PasswordProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupValidator groupValidator;
    private final GroupCountTemplateRepository groupCountTemplateRepository;
    private final GlobalMongoTemplateRepository globalMongoTemplateRepository;

    public GroupCreateRes create(GroupCreateReq req) {

        groupValidator.validateLeaderAndGroupUnique(req.getLeaderName(), req.getGroupName());

        Group group = Group.create(req.getLeaderName(), req.getGroupName(), PasswordProvider.generatePassword());
        group = globalMongoTemplateRepository.saveWithoutDuplicatedId(group, Group.class);

        return GroupDtoMapper.toGroupCreateRes(group.getId());
    }

    public GroupCountRes count() {
        int count = groupCountTemplateRepository.getTotalGroups();
        return GroupDtoMapper.toGroupCountRes(count);
    }
}
