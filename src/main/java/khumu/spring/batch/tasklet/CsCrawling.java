package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.entity.Announcement;
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
public class CsCrawling implements Tasklet {
    private final BoardRepository boardRepository;
    private final AnnouncementRepository announcementRepository;

//    @Autowired
//    public CsCrawling(BoardRepository boardRepository,
//                      AnnouncementRepository announcementRepository) {
//        this.boardRepository = boardRepository;
//        this.announcementRepository = announcementRepository;
//        System.out.println("---1번안됌");
//    }

    @Override
    public RepeatStatus execute(StepContribution stepContri, ChunkContext chunkContext) throws Exception {
        System.out.println("---2번안됌");
        Board board = boardRepository.findById(1L).get();
        System.out.println("---3번안됌");
        List<Announcement> announcements = new ArrayList<>();

        String frontUrl = board.getFrontUrl();
        String backUrl = board.getBackUrl();
        Integer lastId = board.getLastId();

        while(true){
            String page = frontUrl + lastId + backUrl;
            lastId++;

            Document document = Jsoup.connect(page).get();

            if(document == null) {
                break;
            }

            String rawdata = document.select("div.con_area").select("thead").text();

            String title = rawdata.split("ㆍ")[1];
            String date = rawdata.split("ㆍ")[3];

            Announcement announcement = new Announcement(title, page, date);
            announcements.add(announcement);
        }

        announcementRepository.saveAllAndFlush(announcements);

        return RepeatStatus.FINISHED;
    }
}
