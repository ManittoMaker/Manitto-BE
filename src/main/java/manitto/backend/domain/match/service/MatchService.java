package manitto.backend.domain.match.service;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.repository.MatchRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchGetResultRes getUserResult(String groupId, String name, MatchGetResultReq req) {
        return null;
    }
}
