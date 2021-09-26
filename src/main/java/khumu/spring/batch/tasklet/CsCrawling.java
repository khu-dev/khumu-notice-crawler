package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class CsCrawling implements Tasklet, StepExecutionListener {
    private final BoardRepository boardRepository;
    private final AnnouncementRepository announcementRepository;

    private Board board = new Board();
    private List<Announcement> announcements = new ArrayList<>();
    private List<Announcement> savedAnnouncements = new ArrayList<>();

    @Override
    public void beforeStep(StepExecution stepExecution) {
        board = boardRepository.findById(1L).get();
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String frontUrl = board.getFrontUrl();
        String backUrl = board.getBackUrl();
        Integer lastId = board.getLastId();
        Author author = board.getAuthor();

        while(true){
            String page = frontUrl + lastId + backUrl;
            lastId += 1;

            Document document = Jsoup.connect(page).get();

            String rawdata = document.select("div.con_area").select("thead").text();

            String title = rawdata.split("ㆍ")[1];
            title = title.substring(4);
            if (title.isEmpty()) {
                break;
            }
            System.out.println(title);
            String date = rawdata.split("ㆍ")[3];
            date = date.substring(4);

            announcementRepository.save(Announcement.builder()
                    .author(author)
                    .title(title)
                    .date(date)
                    .subLink(page)
                    .build());
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
