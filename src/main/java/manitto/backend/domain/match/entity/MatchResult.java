package manitto.backend.domain.match.entity;

import static manitto.backend.global.util.StringProcessor.isMeaningful;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import manitto.backend.global.exception.CustomException;
import manitto.backend.global.exception.ErrorCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class MatchResult {

    private String giver;
    private String password;
    private String receiver;


    public static MatchResult create(String giver, String password, String receiver) {
        if (!isMeaningful(giver) || !isMeaningful(password) || !isMeaningful(receiver)) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        return MatchResult.builder()
                .giver(giver.trim())
                .password(password.trim())
                .receiver(receiver.trim())
                .build();
    }
}
