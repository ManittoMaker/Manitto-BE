package manitto.backend.domain.match.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.entity.Group;
import manitto.backend.domain.group.repository.GroupRepository;
import manitto.backend.domain.match.dto.mapper.MatchDtoMapper;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.request.MatchStartReq;
import manitto.backend.domain.match.dto.response.MatchAllResultRes;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.entity.Match;
import manitto.backend.domain.match.entity.MatchResult;
import manitto.backend.domain.match.repository.MatchRepository;
import manitto.backend.domain.match.repository.MatchTemplateRepository;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final GroupRepository groupRepository;
    private final MatchTemplateRepository matchTemplateRepository;

    public MatchGetResultRes getUserResult(String groupId, String name, MatchGetResultReq req) {
        Match match = matchTemplateRepository.findMatchResultByGroupIdAndGiverAndPassword(
                groupId, name, req.getPassword());

        String receiver = match.getMatches().get(0).getReceiver();
        return MatchDtoMapper.toMatchGetResultRes(receiver);
    }

    public MatchAllResultRes matchStart(String groupId, MatchStartReq req) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        List<String> names = new ArrayList<>(req.getNames());
        Set<String> uniqueNames = new HashSet<>(names);
        if (names.size() != uniqueNames.size()) {
            throw new CustomException(ErrorCode.MATCH_MEMBER_NAME_DUPLICATED);
        }

        Collections.shuffle(names);
        List<MatchResult> matchResults = IntStream.range(0, names.size())
                .mapToObj(i ->
                        MatchResult.create(
                                names.get(i),
                                "password",
                                names.get((i + 1) % names.size())
                        ))
                .toList();
        Match match = Match.create(group.getId(), matchResults);
        matchRepository.save(match);

        return MatchDtoMapper.toMatchAllResultRes(group.getId(), matchResults);
    }
}
