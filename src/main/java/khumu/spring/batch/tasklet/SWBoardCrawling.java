package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.StepContribution;
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
public class SWBoardCrawling implements Tasklet{
    private final BoardRepository boardRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Board board = boardRepository.findById(3L).get();
        List<Announcement> announcements = new ArrayList<>();

        String fronturl = board.getFrontUrl();
        String backurl = board.getBackUrl();
        Integer lastid = board.getLastId();
        Author author = board.getAuthor();

        while(true) {
            String page = fronturl + lastid + backurl;
            lastid++;

            Document document = Jsoup.connect(page).get();

            if(document == null) {
                break;
            }

            String title = document.select(".bo_v_tit").text();
            if (title.isEmpty()) {
                break;
            }
            System.out.println(title);
            String date = document.select(".if_date").text();
            date = date.substring(4);

            announcementRepository.save(Announcement.builder()
                    .author(author)
                    .title(title)
                    .date(date)
                    .subLink(page)
                    .build());
//            Announcement announcement = Announcement.builder()
//                    .author(author)
//                    .title(title)
//                    .date(date)
//                    .subLink(page)
//                    .build();
//            announcements.add(announcement);
        }

//        announcementRepository.saveAllAndFlush(announcements);

        return RepeatStatus.FINISHED;
    }
}
