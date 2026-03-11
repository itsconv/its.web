package com.itsconv.web.file.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.itsconv.web.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileCleanupScheduler {
    private static final long TEMP_FILE_RETENTION_DAYS = 7L;

    private final FileService fileService;

    // 일주일 이상 지난 임시 파일 삭제
    @Scheduled(cron = "0 0 3 * * *")
    public void purgeExpiredTempEditorFiles() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(TEMP_FILE_RETENTION_DAYS);
        int removedCount = fileService.purgeExpiredTempEditorFiles(cutoff);

        if (removedCount > 0) {
            log.info("Purged expired temp editor files. count={}", removedCount);
        }
    }
}
