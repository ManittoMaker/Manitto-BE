package manitto.backend.domain.group.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.dto.request.GroupCreateReq;
import manitto.backend.domain.group.dto.response.GroupCountRes;
import manitto.backend.domain.group.dto.response.GroupCreateRes;
import manitto.backend.domain.group.dto.response.GroupGetNameRes;
import manitto.backend.domain.group.service.GroupService;
import manitto.backend.global.config.annotation.CustomExceptionDescription;
import manitto.backend.global.config.swagger.SwaggerResponseDescription;
import manitto.backend.global.dto.response.SuccessResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "그룹 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/groups")
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "그룹 생성", description = "그룹의 리더가 새로운 그룹을 생성합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.GROUP_CREATE)
    @PostMapping("")
    public SuccessResponse<GroupCreateRes> create(
            @Validated @RequestBody GroupCreateReq req
    ) {
        return SuccessResponse.ok(groupService.create(req));
    }

    @Operation(summary = "누적 그룹 개수 조회", description = "지금까지 생성되었던 그룹의 총 개수를 조회합니다.")
    @GetMapping("/all")
    public SuccessResponse<GroupCountRes> count() {
        return SuccessResponse.ok(groupService.count());
    }

    @Operation(summary = "그룹명 조회", description = "그룹명을 조회한다.")
    @CustomExceptionDescription(SwaggerResponseDescription.GROUP_GET_NAME)
    @GetMapping("/{groupId}/name")
    public SuccessResponse<GroupGetNameRes> getGroupName(
            @PathVariable("groupId") String groupId
    ) {
        return SuccessResponse.ok(groupService.getGroupName(groupId));
    }
}
