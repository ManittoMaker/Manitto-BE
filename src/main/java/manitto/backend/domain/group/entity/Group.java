package manitto.backend.domain.group.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import manitto.backend.global.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groups")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Group extends BaseEntity {

    @Id
    private String id;
    private String leaderName;
    private String groupName;
    private String password;

    @Override
    public void generateNewId() {
        this.id = generateFirestoreId();
    }

    public static Group create(String leaderName, String groupName, String password) {
        return Group.builder()
                .id(generateFirestoreId())
                .leaderName(leaderName.trim())
                .groupName(groupName.trim())
                .password(password.trim())
                .build();
    }
}
