package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.dto.AnnouncementDto;
import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
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
    private final AuthorRepository authorRepository;

    private Board board = new Board();
    private final List<Announcement> announcements = new ArrayList<>();

    @Override
    public void beforeStep(StepExecution stepExecution) {
        Author author = authorRepository.findByAuthorName("컴퓨터공학과");
        board = boardRepository.findByAuthor(author).get();
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
//            if (title.isEmpty()) {
//                break;
//            }
            String date = rawdata.split("ㆍ")[3];
            date = date.substring(6);
            System.out.println(title);
            System.out.println(date);
            System.out.println(page);

            AnnouncementDto announcement = AnnouncementDto.builder()
                    .title(title)
                    .author(AuthorDto.builder()
                            .author_name(author.getAuthorName())
                            .id(author.getId()).build())
                    .date(date)
                    .sub_link(page)
                    .build();
            var Id = announcementRepository.save(announcement.toEntity()).getId();
            System.out.println(Id);

            announcements.add(announcement.toEntity());

            break;
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
