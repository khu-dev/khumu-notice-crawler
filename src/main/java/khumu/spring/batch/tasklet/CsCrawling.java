package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.entity.Announcement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class CsCrawling implements Tasklet {
    @Bean
    @StepScope
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            String fronturl = item.getFrontUrl();
            String backurl = item.getBackUrl();
            int lastid = item.getLastID();

            while(true){
                String page = fronturl + lastid + backurl;
                lastid++;

                Document document = Jsoup.connect(page).get();

                String rawdata = document.select("div.con_area").select("thead").text();

                String title = rawdata.split("ㆍ")[1];
                String date = rawdata.split("ㆍ")[3];

                Announcement announcement = new Announcement(title, page, date);
            }
            return RepeatStatus.FINISHED;
        };
    }
}
