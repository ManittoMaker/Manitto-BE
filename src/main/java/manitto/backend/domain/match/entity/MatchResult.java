package manitto.backend.domain.match.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResult {

    private String giver;
    private String password;
    private String receiver;


    public static MatchResult create(String giver, String password, String receiver) {
        return MatchResult.builder()
                .giver(giver)
                .password(password)
                .receiver(receiver)
                .build();
    }
}
