package khumu.spring.batch.job;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.WebUrl;
import lombok.extern.slf4j.XSlf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

@XSlf4j
@Configuration
public class CsBoardCrawlingConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunksize = 1;
    
    public CsBoardCrawlingConfiguration(JobBuilderFactory jobBuilderFactory,
                                        StepBuilderFactory stepBuilderFactory,
                                        EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }
    
    @Bean
    public Job csBoardJob() {
        return this.jobBuilderFactory.get("csBoardJob")
                .incrementer(new RunIdIncrementer())
                .start(this.csBoardStep())
                .build();
    }

    @Bean
    @JobScope
    public Step csBoardStep() {
        return this.stepBuilderFactory.get("csBoardStep")
                .tasklet()
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Announcement> csBoardItemWriter() throws Exception {
        JpaItemWriter itemWriter = new JpaItemWriterBuilder<Announcement>()
                .entityManagerFactory(entityManagerFactory)
                .build();
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    @StepScope
    public ItemProcessor<WebUrl, Announcement> csBoardItemProcessor() {
        return item -> {
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
            return announcement;
        };
    }

    @Bean
    @StepScope
    public ItemReader<WebUrl> csBoardItemReader() throws Exception {
        JpaCursorItemReader itemReader = new JpaCursorItemReaderBuilder<WebUrl>()
                .name("csBoardItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select p from Weburl")
                .build();
        itemReader.afterPropertiesSet();
        return itemReader;
    }
}
