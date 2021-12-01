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
public class ArtDesignCrawling implements Tasklet, StepExecutionListener {
    private final BoardRepository boardRepository;
    private final AuthorRepository authorRepository;
    private final AnnouncementRepository announcementRepository;
    private final EventPublish eventPublish;

    // 멱등성을 위한 board 데이터 주입
    @Override
    public void beforeStep(StepExecution stepExecution) {
        Author author = Author.builder()
                .id(1L)
                .authorName("예술디자인대학").build();
        authorRepository.save(author);

        Integer boardLastId = boardRepository.findByAuthorId(author.getId()).getLastId();

        Board board = Board.builder()
                .id(1L)
                .frontUrl("http://and.khu.ac.kr/board/bbs/board.php?bo_table=05_01&wr_id=")
                .backUrl("")
                .lastId(boardLastId)
                .author(author).build();

        boardRepository.save(board);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Author target = authorRepository.findByAuthorName("예술디자인대학");
        Board board = boardRepository.findByAuthor(target).get();

        String frontUrl = board.getFrontUrl();
        String backUrl = board.getBackUrl();
        Integer lastId = board.getLastId();
        Author author = board.getAuthor();
        String authorName = author.getAuthorName();

        while(true) {
            // 공지사항 URL 만들기
            String page = frontUrl + lastId;
            lastId += 1;

            // URL 연결
            Document document = Jsoup.connect(page).get();

            // Data 긁어 오기
            String title = document.select(".bo_v_title").text();
            String date = document.select(".bo_v_file").select("span").text();

            // 불러올 데이터가 없을 시, 멈추고 lastId Update
            if (title.isEmpty()) {
                boardRepository.save(Board.builder()
                        .id(board.getId())
                        .author(target)
                        .frontUrl(frontUrl)
                        .backUrl(backUrl)
                        .lastId(lastId).build());
                System.out.println("작업 종료");

                break;
            }

            // 긁은 공지사항 DTO 객체 생성
            AnnouncementDto announcementDto = AnnouncementDto.builder()
                    .title(title)
                    .author(AuthorDto.builder()
                            .id(author.getId())
                            .authorName(authorName)
                            .build())
                    .date(date)
                    .subLink(page)
                    .build();

            // 메세지 큐 전송
            eventPublish.pubTopic(announcementDto);
            // DB Data Write
            announcementRepository.save(announcementDto.toEntity());
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
