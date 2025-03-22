package manitto.backend.domain.group.dto.GroupDtoMapper;

import manitto.backend.domain.group.dto.response.GroupCountRes;
import manitto.backend.domain.group.dto.response.GroupCreateRes;
import manitto.backend.domain.group.dto.response.GroupGetNameRes;

public class GroupDtoMapper {

    public static GroupCreateRes toGroupCreateRes(String groupId, String leaderName, String groupName,
                                                  String password) {
        return GroupCreateRes.builder()
                .groupId(groupId)
                .leaderName(leaderName)
                .groupName(groupName)
                .password(password)
                .build();
    }

    public static GroupCountRes toGroupCountRes(int count) {
        return GroupCountRes.builder()
                .count(count)
                .build();
    }

    public static GroupGetNameRes toGroupGetNameRes(String groupName) {
        return GroupGetNameRes.builder()
                .groupName(groupName)
                .build();
    }
}
