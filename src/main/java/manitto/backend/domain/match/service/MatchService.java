package manitto.backend.domain.match.service;

import static manitto.backend.global.util.ResponseUtil.getSingleResult;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.global.dto.response.result.SingleResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public SingleResult<MatchGetResultRes> getUserResult(String groupId, String name, MatchGetResultReq req) {
        return getSingleResult(null);
    }
}
