package manitto.backend.domain.match.dto.mapper;

import manitto.backend.domain.match.dto.response.MatchGetResultRes;

public class MatchDtoMapper {

    public static MatchGetResultRes toMatchGetResultRes(String receiver) {
        return MatchGetResultRes.builder()
                .receiver(receiver)
                .build();
    }
}
