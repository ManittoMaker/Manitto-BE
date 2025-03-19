package manitto.backend.global.config;

import lombok.RequiredArgsConstructor;
import manitto.backend.domain.group.entity.GroupCount;
import manitto.backend.domain.group.repository.GroupCountRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
@RequiredArgsConstructor
public class GroupCountInitializer {

    private final GroupCountRepository groupCountRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeGroupCount() {
        if (!groupCountRepository.existsById("groupCount")) {
            groupCountRepository.save(GroupCount.create());
        }
    }
}
