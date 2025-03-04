package manitto.backend.testUtil;

import manitto.backend.domain.group.dto.request.GroupCreateReq;

public class GroupDtoMother {
    public static GroupCreateReq createGroupCreateReq(String groupName, String leaderName) {
        GroupCreateReq dto = new GroupCreateReq();
        dto.setGroupName(groupName);
        dto.setLeaderName(leaderName);

        return dto;
    }
}
