package khumu.spring.batch.tasklet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

public class SWBoardCrawling {
    @Bean
    @StepScope
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            String fronturl = item.getFrontUrl();
            String backurl = item.getBackUrl();
            int lastid = item.getLastID();

            while(true) {
                String page = fronturl + lastid + backurl;
                lastid++;
                Document document = Jsoup.connect(page).get();

                String title = document.select(".bo_v_tit").text();
                if(title == "Null") {
                    lastid--;
                    item.setLastid(lastid);
                    break;
                }
                String sublink = page;
                String date = document.select(".if_date").text();
            }
            return RepeatStatus.FINISHED;
        };
    }
}
