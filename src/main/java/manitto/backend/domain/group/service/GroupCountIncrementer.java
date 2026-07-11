package manitto.backend.domain.group.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import manitto.backend.domain.group.repository.GroupCountTemplateRepository;
import manitto.backend.global.config.app.AsyncConfig;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GroupCountIncrementer {

    private static final int MAX_RETRY = 3;
    private static final long INITIAL_DELAY_MS = 100;

    private final GroupCountTemplateRepository groupCountTemplateRepository;

    @Async(AsyncConfig.COUNT_UPDATE_EXECUTOR)
    public void increment() {
        long delay = INITIAL_DELAY_MS;
        for (int attempt = 0; attempt < MAX_RETRY; attempt++) {
            try {
                if (attempt == 1) {
                    log.info("[GROUP COUNT UPDATE FAILED] 재시도 수행");
                }
                groupCountTemplateRepository.updateTotalGroups();
                return;
            } catch (Exception e) {
                if (attempt == MAX_RETRY - 1) {
                    log.warn("[GROUP COUNT UPDATE FAILED] 재시도 소진 (시도 횟수: [{}]), 오류: [{}]", MAX_RETRY, e.getMessage());
                    return;
                }
            }
            if (!sleep(delay)) {
                return;
            }
            delay *= 2;
        }
    }

    private boolean sleep(long millis) {
        try {
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
