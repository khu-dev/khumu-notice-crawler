package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.BoardRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
public class SWBoardCrawling implements Tasklet{
    private final BoardRepository boardRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public SWBoardCrawling(BoardRepository boardRepository,
                           AnnouncementRepository announcementRepository) {
        this.boardRepository = boardRepository;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contributionm, ChunkContext chunkContext) throws Exception {
        Board board = boardRepository.getById(3L);
        List<Announcement> announcements = new ArrayList<>();

        String fronturl = board.getFrontUrl();
        String backurl = board.getBackUrl();
        Integer lastid = board.getLastId();

        while(true) {
            String page = fronturl + lastid + backurl;
            lastid++;

            Document document = Jsoup.connect(page).get();

            if(document == null) {
                break;
            }

            String title = document.select(".bo_v_tit").text();
            String date = document.select(".if_date").text();

            Announcement announcement = new Announcement(title, page, date);
            announcements.add(announcement);
        }

        announcementRepository.saveAllAndFlush(announcements);

        return RepeatStatus.FINISHED;
    }
}
