package manitto.backend.domain.match.service;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.repository.MatchRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
}
