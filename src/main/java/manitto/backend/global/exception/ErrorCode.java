package manitto.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 에러코드 규약
     * HTTP Status Code는 에러에 가장 유사한 코드를 부여한다.(Swagger에서 구분하여 Response Example로 출력된다.)
     * 사용자정의 에러코드는 음수를 사용한다.
     * 사용자정의 에러코드는 중복되지 않게 배정한다.
     * 사용자정의 에러코드는 각 카테고리 별로 100단위씩 끊어서 배정한다. 단, Common 카테고리는 -100 단위를 고정으로 가져간다.
     */

    /**
     * 401 : 미승인 403 : 권한의 문제가 있을때 406 : 객체가 조회되지 않을 때 409 : 현재 데이터와 값이 충돌날 때(ex. 아이디 중복) 412 : 파라미터 값이 뭔가 누락됐거나 잘못 왔을 때
     * 422 : 파라미터 문법 오류 424 : 뭔가 단계가 꼬였을때, 1번안하고 2번하고 그런경우
     */

    //Common
    SERVER_UNTRACKED_ERROR(-100, "미등록 서버 에러입니다. 서버 팀에 연락주세요.", 500),
    OBJECT_NOT_FOUND(-101, "조회된 객체가 없습니다.", 406),
    INVALID_PARAMETER(-102, "잘못된 파라미터입니다.", 422),
    PARAMETER_VALIDATION_ERROR(-103, "파라미터 검증 에러입니다.", 422),
    PARAMETER_GRAMMAR_ERROR(-104, "파라미터 문법 에러입니다.", 422),
    INVALID_TYPE_PARAMETER(-106, "잘못된 타입 파라미터입니다.", 422),
    NOT_FOUND_PATH(-108, "존재하지 않는 API 경로입니다.", 404),
    CRITICAL_ID_ERROR(-109, "엔티티 ID 설정에 오류가 발생했습니다.", 500),

    // Match
    MATCH_NOT_VALID(-300, "유효하지 않은 매치입니다.", 400),
    MATCH_NOT_FOUND(-301, "조회된 매치가 없습니다.", 406),
    MATCH_INTEGRITY_VIOLATION(-302, "유효하지 않은 매치 정보가 검출되었습니다.", 500),
    MATCH_MEMBER_NAME_DUPLICATED(-303, "멤버 이름은 중복되면 안됩니다.", 400),
    MATCH_ALREADY_EXIST(-304, "이미 매칭된 그룹입니다.", 409),

    // Group
    GROUP_NOT_FOUND(-400, "조회된 그룹이 없습니다.", 406),
    GROUP_INTEGRITY_VIOLATION(-402, "유효하지 않은 그룹 정보가 검출되었습니다.", 500),
    GROUP_LEADER_AND_GROUP_NAME_DUPLICATED(-403, "이미 동일한 리더 이름, 그룹 이름을 사용하는 그룹이 존재합니다.", 400),
    ;

    private final int errorCode;
    private final String message;
    private final int httpCode;
}
