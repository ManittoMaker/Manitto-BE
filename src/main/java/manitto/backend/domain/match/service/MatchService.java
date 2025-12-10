package manitto.backend.domain.match.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupTemplateRepository;
import manitto.backend.domain.group.service.GroupValidator;
import manitto.backend.domain.match.dto.mapper.MatchDtoMapper;
import manitto.backend.domain.match.dto.request.MatchGetGroupResultReq;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.request.MatchStartReq;
import manitto.backend.domain.match.dto.response.MatchAllResultRes;
import manitto.backend.domain.match.dto.response.MatchGetGroupResultRes;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.domain.match.repository.MatchTemplateRepository;
import manitto.backend.global.repository.GlobalMongoTemplateRepository;
import manitto.backend.global.util.StringListProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchTemplateRepository matchTemplateRepository;
    private final GlobalMongoTemplateRepository globalMongoTemplateRepository;
    private final GroupTemplateRepository groupTemplateRepository;

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

        List<String> names = StringListProcessor.filterNotBlank(req.getNames());
        matchValidator.validateMinimumSize(names);
        matchValidator.validateDuplicateName(names);

        List<MatchResult> matchResults = matchProcessor.matching(names);
        Match match = Match.create(groupId, matchResults);
        globalMongoTemplateRepository.saveWithoutDuplicatedId(match, Match.class);

        return MatchDtoMapper.toMatchAllResultRes(groupId, matchResults);
    }

    public MatchGetGroupResultRes getGroupResult(MatchGetGroupResultReq req) {
        Group group = groupTemplateRepository.findGroupByLeaderNameAndGroupNameAndPassword(req.getLeaderName(),
                req.getGroupName(), req.getPassword());
        Match match = matchTemplateRepository.findMatchByGroupId(group.getId());

        return MatchDtoMapper.toMatchGetGroupResultRes(group.getId(), match.getMatches());
    }
}
