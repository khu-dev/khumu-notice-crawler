package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.BoardRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CsCrawling implements Tasklet {
    private final BoardRepository boardRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public CsCrawling(BoardRepository boardRepository,
                      AnnouncementRepository announcementRepository) {
        this.boardRepository = boardRepository;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contributionm, ChunkContext chunkContext) throws Exception {
        Board rawdata = boardRepository.findByLastid(2330);
        System.out.println(rawdata);

//        Optional<Board> rawdata = webUrlRepository.findByLastid(1l);
//        rawdata.ifPresent(selectWebUrl -> {
//            System.out.println(rawdata);
//        });

//        String fronturl = contribution.getFrontUrl();
//        String backurl = item.getBackUrl();
//        int lastid = item.getLastID();
//
//        while(true){
//            String page = fronturl + lastid + backurl;
//            lastid++;
//
//            Document document = Jsoup.connect(page).get();
//
//            String rawdata = document.select("div.con_area").select("thead").text();
//
//            String title = rawdata.split("ㆍ")[1];
//            String date = rawdata.split("ㆍ")[3];
//
//            Announcement announcement = new Announcement(title, page, date);
//        }
        return RepeatStatus.FINISHED;
    }
}
