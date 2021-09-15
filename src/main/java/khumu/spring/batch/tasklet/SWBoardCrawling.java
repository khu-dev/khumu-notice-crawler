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
        Board rawdata = boardRepository.findByLastid(1653);
        System.out.println(rawdata);

//        Optional<Board> rawdata = webUrlRepository.findByLastid(3l);
//        rawdata.ifPresent(selectWebUrl -> {
//            System.out.println(rawdata);
//        });

//        String fronturl = webUrlRepository.getFrontUrl();
//        String backurl = webUrlRepository.getBackUrl();
//        int lastid = webUrlRepository.getLastID();
//
//        while(true) {
//            String page = fronturl + lastid + backurl;
//            lastid++;
//            Document document = Jsoup.connect(page).get();
//
//            String title = document.select(".bo_v_tit").text();
//            if(title == "Null") {
//                lastid--;
//                webUrlRepository.setLastid(lastid);
//                break;
//            }
//            String sublink = page;
//            String date = document.select(".if_date").text();
//        }
        return RepeatStatus.FINISHED;
    }
}
