package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.entity.WebUrl;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.WebUrlRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SWBoardCrawling implements Tasklet{
    private final WebUrlRepository webUrlRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public SWBoardCrawling(WebUrlRepository webUrlRepository,
                           AnnouncementRepository announcementRepository) {
        this.webUrlRepository = webUrlRepository;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contributionm, ChunkContext chunkContext) throws Exception {
        Optional<WebUrl> rawdata = webUrlRepository.findById(3l);
        System.out.println(rawdata);
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
