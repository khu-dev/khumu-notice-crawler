package khumu.spring.batch.job;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.WebUrl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

@Slf4j
@Configuration
public class SWBoardCrawlingConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    public SWBoardCrawlingConfiguration(JobBuilderFactory jobBuilderFactory,
                                        StepBuilderFactory stepBuilderFactory,
                                        EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public Job swBoardJob() {
        return this.jobBuilderFactory.get("swBoardJob")
                .incrementer(new RunIdIncrementer())
                .start(this.swBoardStep())
                .build();
    }

    @Bean
    @JobScope
    public Step swBoardStep() {
        return this.stepBuilderFactory.get("swBoardStep")
                .chunk(1)
                .reader(this.swBoardItemReader())
                .processor(this.swBoardItemProcessor())
                .writer(this.swBoardItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Announcement> swBoardItemWriter() throws Exception {
        JpaItemWriter itemWriter = new JpaItemWriterBuilder<Announcement>()
                .entityManagerFactory(entityManagerFactory)
                .build();
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    @StepScope
    public ItemProcessor<WebUrl, Announcement> swBoardItemProcessor() throws Exception {
        return item -> new Announcement() {
            String target = item.getUrl();
            Document connectcheck;
            try {
                connectcheck = Jsoup.connect(target + "1").get();
            } catch (
            IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {

                String page = item.getUrl() + i;

                Document document = Jsoup.connect(page).get();
                String title = document.select("tr.bo_notice").select("td.td_subject").select("div.bo_tit").text();
                String sublink = document.select("tr.bo_notice").select("td.td_subject").select("div.bo_tit").select().text();
                String date = document.select().text();
                String author = document.select().text();

            }
        };
    }

    @Bean
    @StepScope
    public ItemReader<WebUrl> swBoardItemReader() throws Exception {
        JpaCursorItemReader itemReader = new JpaCursorItemReaderBuilder<WebUrl>()
                .name("swBoardItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select p from WebUrl")
                .build();
        itemReader.afterPropertiesSet();
        return itemReader;
    }
}
