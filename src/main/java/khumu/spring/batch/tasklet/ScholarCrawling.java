package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.dto.AnnouncementDto;
import khumu.spring.batch.data.dto.AuthorDto;
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

@Component
@StepScope
@RequiredArgsConstructor
public class ScholarCrawling implements Tasklet, StepExecutionListener {
    private final BoardRepository boardRepository;
    private final AuthorRepository authorRepository;
    private final AnnouncementRepository announcementRepository;
    private final EventPublish eventPublish;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        Author author = Author.builder()
                .id(7L)
                .authorName("학생지원센터장학").build();
        authorRepository.save(author);

        Integer boardLastId = boardRepository.findByAuthorId(author.getId()).getLastId();

        Board board = Board.builder()
                .id(7L)
                .frontUrl("http://janghak.khu.ac.kr/board/bbs/board.php?bo_table=06_01&wr_id=")
                .backUrl("")
                .lastId(2415)
                .author(author).build();

        boardRepository.save(board);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Author target = authorRepository.findByAuthorName("학생지원센터장학");
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

            String rawData = document.select("[style=border-top:1px solid #B41C1B; border-bottom:1px solid #E7E7E7; background-color:#F5F5F5; clear:both; height:30px; padding:8px 7px 0 7px]").text();
            String title = document.select("[style=color:#b41c1b; font-size:12px; word-break:break-all;]").text();

            if (title.isEmpty()) {
                boardRepository.save(Board.builder()
                        .id(board.getId())
                        .lastId(lastId)
                        .frontUrl(frontUrl)
                        .backUrl(backUrl)
                        .author(author)
                        .build());
                System.out.println("=====작업 종료=====");
                break;
            }

            System.out.println(title);

            String date = rawData.replace(title + " 관리자 ", "");
            date = date.substring(0, 14);

            AnnouncementDto announcement = AnnouncementDto.builder()
                    .title(title)
                    .author(AuthorDto.builder()
                            .id(author.getId())
                            .authorName(authorName)
                            .build())
                    .date(date)
                    .subLink(page)
                    .build();
            eventPublish.pubTopic(announcement);
            System.out.println("=====메세지 전송=====");
            announcementRepository.save(announcement.toEntity());
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
