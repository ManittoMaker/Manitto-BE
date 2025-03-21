package manitto.backend.domain.match.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.match.dto.request.MatchGetGroupResultReq;
import manitto.backend.domain.match.dto.request.MatchGetResultReq;
import manitto.backend.domain.match.dto.request.MatchStartReq;
import manitto.backend.domain.match.dto.response.MatchGetFinalResultRes;
import manitto.backend.domain.match.dto.response.MatchGetGroupResultRes;
import manitto.backend.domain.match.dto.response.MatchGetResultRes;
import manitto.backend.domain.match.service.MatchService;
import manitto.backend.global.config.annotation.CustomExceptionDescription;
import manitto.backend.global.config.swagger.SwaggerResponseDescription;
import manitto.backend.global.dto.response.SuccessResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "매칭 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/matches")
public class MatchController {

    private final MatchService matchService;

    @Operation(summary = "매칭 결과 조회(개인)", description = "특정 그룹의 단일 사용자에 대한 매칭 결과를 조회합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.MATCH_GET_USER_RESULT)
    @GetMapping("/{groupId}/result/user/{name}")
    public SuccessResponse<MatchGetResultRes> getUserResult(
            @PathVariable("groupId") String groupId,
            @PathVariable("name") String name,
            @Validated @ModelAttribute MatchGetResultReq req
    ) {
        return SuccessResponse.ok(matchService.getUserResult(groupId, name, req));
    }

    @Operation(summary = "매칭 시작", description = "그룹의 매칭을 진행합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.MATCH_START)
    @PostMapping("/{groupId}")
    public SuccessResponse<Object> matchStart(
            @PathVariable("groupId") String groupId,
            @Validated @RequestBody MatchStartReq req
    ) {
        return SuccessResponse.ok(matchService.matchStart(groupId, req));
    }

    @Operation(summary = "최종 매칭 결과 조회(전체)", description = "그룹 매칭 완료 후 모든 사용자에 대한 매칭 결과를 조회합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.MATCH_GET_FINAL_RESULT)
    @GetMapping("/{groupId}")
    public SuccessResponse<MatchGetFinalResultRes> getFinalResult(
            @PathVariable("groupId") String groupId
    ) {
        return SuccessResponse.ok(matchService.getFinalResult(groupId));
    }

    @Operation(summary = "기존 그룹 매칭 결과 조회(전체)", description = "특정 그룹의 모든 사용자에 대한 매칭 결과를 조회합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.MATCH_GET_GROUP_RESULT)
    @GetMapping("/results")
    public SuccessResponse<MatchGetGroupResultRes> getGroupResult(
            @Validated @ParameterObject MatchGetGroupResultReq req
    ) {
        return SuccessResponse.ok(matchService.getGroupResult(req));
    }
}
