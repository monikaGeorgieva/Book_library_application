package book_library_app.scheduler;


import book_library_app.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryStatisticsScheduler {

    private final BookRepository bookRepository;

    /**
     * Cron job – изпълнява се всеки ден в 00:00 формата е: секунда минута час ден месец ден-от-седмицата
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void logDailyBookCount() {
        long count = bookRepository.count();
        log.info("[Daily Stats] Брой книги в библиотеката: {}", count);
    }

    /**
      fixedRate – изпълнява се на всеки 60 секунди,независимо кога е приключил предишният run.
     */
    @Scheduled(fixedRate = 60000)
    public void heartbeat() {
        log.info("[Scheduler Heartbeat] Library scheduler работи…");
    }
}
