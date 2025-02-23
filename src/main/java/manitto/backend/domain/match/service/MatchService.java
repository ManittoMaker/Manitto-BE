package manitto.backend.domain.match.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.dto.mapper.MatchDtoMapper;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchGetResultRes getUserResult(String groupId, String name, MatchGetResultReq req) {
        List<Match> matches = matchRepository.findMatchResultByGroupIdAndGiverAndPassword(
                groupId, name, req.getPassword());
        if (matches.isEmpty()) {
            throw new CustomException(ErrorCode.MATCH_NOT_FOUND);
        }
        if (matches.size() > 1) {
            throw new CustomException(ErrorCode.MATCH_INTEGRITY_VIOLATION);
        }
        if (matches.get(0).getMatches().size() > 1) {
            throw new CustomException(ErrorCode.MATCH_INTEGRITY_VIOLATION);
        }
        String receiver = matches.get(0).getMatches().get(0).getReceiver();
        return MatchDtoMapper.toMatchGetResultRes(receiver);
    }
}
