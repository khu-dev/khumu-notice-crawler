package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
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
public class LINCCrawling implements Tasklet, StepExecutionListener {
    private final BoardRepository boardRepository;
    private final AuthorRepository authorRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        Author author = Author.builder()
                .id(6L)
                .authorName("LINC+ 사업단").build();
        authorRepository.save(author);

        Integer boardLastId = boardRepository.findByAuthorId(author.getId()).getLastId();

        Board board = Board.builder()
                .id(6L)
                .frontUrl("http://lincplus.khu.ac.kr/linc8/linc_notice_view.do?lbSeq=")
                .lastId(boardLastId).build();

        boardRepository.save(board);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
