package manitto.backend.domain.match.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.service.GroupValidator;
import manitto.backend.domain.match.dto.mapper.MatchDtoMapper;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.request.MatchStartReq;
import manitto.backend.domain.match.dto.response.MatchAllResultRes;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.domain.match.repository.MatchTemplateRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchTemplateRepository matchTemplateRepository;

    private final MatchValidator matchValidator;
    private final GroupValidator groupValidator;
    private final MatchProcessor matchProcessor;

    public MatchGetResultRes getUserResult(String groupId, String name, MatchGetResultReq req) {
        Match match = matchTemplateRepository.findMatchResultByGroupIdAndGiverAndPassword(
                groupId, name, req.getPassword());

        String receiver = match.getMatches().get(0).getReceiver();
        return MatchDtoMapper.toMatchGetResultRes(receiver);
    }

    public MatchAllResultRes matchStart(String groupId, MatchStartReq req) {
        groupValidator.validateExists(groupId);
        matchValidator.validateAlreadyExists(groupId);
        matchValidator.validateDuplicateName(req.getNames());

        List<MatchResult> matchResults = matchProcessor.matching(req.getNames());
        Match match = Match.create(groupId, matchResults);
        matchRepository.save(match);

        return MatchDtoMapper.toMatchAllResultRes(groupId, matchResults);
    }
}
