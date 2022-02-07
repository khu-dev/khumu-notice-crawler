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
public class SWConCrawling implements Tasklet, StepExecutionListener {
    private final BoardRepository boardRepository;
    private final AuthorRepository authorRepository;
    private final AnnouncementRepository announcementRepository;
    private final EventPublish eventPublish;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        Author author = Author.builder()
                .id(9L)
                .authorName("SW융합대학").build();
        authorRepository.save(author);

        Integer boardLastId = boardRepository.findByAuthorId(author.getId()).getLastId();

        Board board = Board.builder()
                .id(9L)
                .frontUrl("http://software.khu.ac.kr/board5/bbs/board.php?bo_table=05_01&wr_id=")
                .backUrl("")
                .lastId(boardLastId)
                .author(author).build();

        boardRepository.save(board);

    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Author target = authorRepository.findByAuthorName("SW융합대학").get();
        Board board = boardRepository.findByAuthor(target).get();

        String frontUrl = board.getFrontUrl();
        String backUrl = board.getBackUrl();
        Integer lastId = board.getLastId();
        Author author = board.getAuthor();
        String authorName = author.getAuthorName();

//        while(true) {
//            String page = frontUrl + lastId + backUrl;
//            lastId += 1;
//
//            Document document = Jsoup.connect(page).get();
//
//            String title = document.select(".bo_v_tit").text();
//
//            if(title.isEmpty()) {
//                boardRepository.save(Board.builder()
//                        .id(board.getId())
//                        .lastId(lastId)
//                        .frontUrl(frontUrl)
//                        .backUrl(backUrl)
//                        .author(author)
//                        .build());
//                System.out.println("=====작업 종료=====");
//                break;
//            }
//
//            System.out.println(title);
//
//            String date = document.select(".if_date").text();
//
//            AnnouncementDto announcementDto = AnnouncementDto.builder()
//                    .title(title)
//                    .author(AuthorDto.builder()
//                            .id(author.getId())
//                            .authorName(authorName)
//                            .build())
//                    .date(date)
//                    .subLink(page)
//                    .build();
////            eventPublish.pubTopic(announcementDto);
//            System.out.println("=====메세지 전송=====");
//            announcementRepository.save(announcementDto.toEntity());
//        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
