package manitto.backend.testUtil;

import manitto.backend.domain.match.dto.request.MatchGetResultReq;

public class MatchDtoMother {

    public static MatchGetResultReq createMatchGetResultReq(String password) {
        MatchGetResultReq dto = new MatchGetResultReq();
        dto.setPassword(password);
        return dto;
    }
}
