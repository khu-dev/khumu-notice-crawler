package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.dto.AnnouncementDto;
import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.publish.EventPublish;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.AuthorRepository;
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
public class SWBoardCrawling implements Tasklet, StepExecutionListener {
    private final BoardRepository boardRepository;
    private final AuthorRepository authorRepository;
    private final AnnouncementRepository announcementRepository;
    private final EventPublish eventPublish;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        Author author = Author.builder()
                .id(8L)
                .authorName("소프트웨어중심대학사업단").build();
        authorRepository.save(author);

        Integer boardLastId = boardRepository.findByAuthorId(author.getId()).getLastId();

        Board board = Board.builder()
                .id(8L)
                .frontUrl("http://swedu.khu.ac.kr/board5/bbs/board.php?bo_table=06_01&wr_id=")
                .lastId(1684)
                .author(author).build();

        boardRepository.save(board);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Author target = authorRepository.findByAuthorName("소프트웨어중심대학사업단");
        Board board = boardRepository.findByAuthor(target).get();

        String frontUrl = board.getFrontUrl();
        String backUrl = board.getBackUrl();
        Integer lastId = board.getLastId();
        Author author = board.getAuthor();
        String authorName = board.getAuthor().getAuthorName();

        while(true) {
            String page = frontUrl + lastId + backUrl;
            lastId++;

            Document document = Jsoup.connect(page).get();

            if(document == null) {
                break;
            }

            String title = document.select(".bo_v_tit").text();
            if (title.isEmpty()) {
                boardRepository.save(Board.builder()
                        .id(board.getId())
                        .lastId(lastId)
                        .frontUrl(frontUrl)
                        .backUrl(backUrl)
                        .author(author)
                        .build());
                break;
            }
            System.out.println(title);
            String date = document.select(".if_date").text();
            date = date.substring(4);

            AnnouncementDto announcement = AnnouncementDto.builder()
                    .title(title)
                    .author(AuthorDto.builder()
                            .id(author.getId())
                            .author_name(authorName)
                            .build())
                    .date(date)
                    .sub_link(page)
                    .build();
            eventPublish.pubTopic(announcement);

            announcementRepository.save(announcement.toEntity());
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
