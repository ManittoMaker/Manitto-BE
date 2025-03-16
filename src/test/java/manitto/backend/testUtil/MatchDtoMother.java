package manitto.backend.testUtil;

import java.util.List;
import manitto.backend.domain.match.dto.request.MatchGetGroupResultReq;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.request.MatchStartReq;

public class MatchDtoMother {

    public static MatchGetResultReq createMatchGetResultReq(String password) {
        MatchGetResultReq dto = new MatchGetResultReq();
        dto.setPassword(password);
        return dto;
    }

    public static MatchStartReq createMatchStartReq(List<String> names) {
        MatchStartReq dto = new MatchStartReq();
        dto.setNames(names);
        return dto;
    }

    public static MatchGetGroupResultReq createMatchGetGroupResultReq(String leaderName, String groupName,
                                                                      String password) {
        MatchGetGroupResultReq dto = new MatchGetGroupResultReq();
        dto.setLeaderName(leaderName);
        dto.setGroupName(groupName);
        dto.setPassword(password);
        return dto;
    }
}
