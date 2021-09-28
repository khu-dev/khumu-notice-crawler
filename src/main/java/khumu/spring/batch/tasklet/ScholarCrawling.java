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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class ScholarCrawling implements Tasklet {
    private final BoardRepository boardRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Board board = boardRepository.findById(2L).get();
        List<Announcement> announcements = new ArrayList<>();

        String frontUrl = board.getFrontUrl();
        String backUrl = board.getBackUrl();
        Integer lastId = board.getLastId();
        Author author = board.getAuthor();

        while(true) {
            String page = frontUrl + lastId + backUrl;
            lastId++;

            Document document = Jsoup.connect(page).get();

            if(document == null) {
                break;
            }

            String rawdata = document.select("[style=border-top:1px solid #B41C1B; border-bottom:1px solid #E7E7E7; background-color:#F5F5F5; clear:both; height:30px; padding:8px 7px 0 7px]").text();

            String title = document.select("[style=color:#b41c1b; font-size:12px; word-break:break-all;]").text();
            if (title.isEmpty()) {
                break;
            }
            System.out.println(title);
            String date = rawdata.replace(title + " 관리자 ", "");
            date = date.substring(0, 14);

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
//        announcementRepository.saveAll(announcements);
        return RepeatStatus.FINISHED;
    }
}
