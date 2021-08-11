package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.WebUrl;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.WebUrlRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CsCrawling implements Tasklet {
    private final WebUrlRepository webUrlRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public CsCrawling(WebUrlRepository webUrlRepository,
                      AnnouncementRepository announcementRepository) {
        this.webUrlRepository = webUrlRepository;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contributionm, ChunkContext chunkContext) throws Exception {
        Optional<WebUrl> rawdata = webUrlRepository.findById(1l);
        rawdata.ifPresent(selectWebUrl -> {
            System.out.println(selectWebUrl.getFrontUrl());
        });
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
