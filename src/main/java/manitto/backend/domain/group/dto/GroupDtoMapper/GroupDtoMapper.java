package manitto.backend.domain.group.dto.GroupDtoMapper;

import manitto.backend.domain.group.dto.response.GroupCreateRes;

public class GroupDtoMapper {

    public static GroupCreateRes toGroupCreateRes(String groupId) {
        return GroupCreateRes.builder()
                .groupId(groupId)
                .build();
    }
}
