package manitto.backend.domain.group.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groupCount")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupCount {

    @Id
    private String id = "groupCount";
    private int totalGroups;

    public static GroupCount create() {
        return new GroupCount("groupCount", 0);
    }
}
