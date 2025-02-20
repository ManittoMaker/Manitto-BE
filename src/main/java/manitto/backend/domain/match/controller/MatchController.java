package manitto.backend.domain.match.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.service.MatchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "매칭 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/match")
public class MatchController {

    private final MatchService matchService;

    
}
