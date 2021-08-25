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
@StepScope
public class ScholarCrawling implements Tasklet {
    private final WebUrlRepository webUrlRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public ScholarCrawling(WebUrlRepository webUrlRepository,
                           AnnouncementRepository announcementRepository) {
        this.webUrlRepository = webUrlRepository;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contributionm, ChunkContext chunkContext) throws Exception {
        WebUrl rawdata = webUrlRepository.findByLastid(2411);
        System.out.println(rawdata);

//        Optional<WebUrl> rawdata = webUrlRepository.findByLastid(2l);
//        rawdata.ifPresent(selectWebUrl -> {
//            System.out.println(rawdata);
//        });

//        String page = fronturl + lastid + backurl;
//        lastid++;
//        Document document = Jsoup.connect(page).get();
//        String rawdata = document.select("[style=border-top:1px solid #B41C1B; border-bottom:1px solid #E7E7E7; background-color:#F5F5F5; clear:both; height:30px; padding:8px 7px 0 7px]").text();
//
//        String title = document.select("[style=color:#b41c1b; font-size:12px; word-break:break-all;]").text();
//        String date = rawdata.replace(title + " 관리자 ", "");
//        date = date.substring(0, 14);
//
//        if(title == "Null") {
//            lastid--;
//            item.setLastid(lastid);
//            break;
//        }
//        String sublink = page;
        return RepeatStatus.FINISHED;
    }
}
