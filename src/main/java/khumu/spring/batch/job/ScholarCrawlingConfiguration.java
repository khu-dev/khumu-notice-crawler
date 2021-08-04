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
public class ScholarCrawlingConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    public ScholarCrawlingConfiguration(JobBuilderFactory jobBuilderFactory,
                                        StepBuilderFactory stepBuilderFactory,
                                        EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public Job scholarJob() {
        return this.jobBuilderFactory.get("scholarJob")
                .incrementer(new RunIdIncrementer())
                .start(this.scholarStep())
                .build();
    }

    @Bean
    @JobScope
    public Step scholarStep() {
        return this.stepBuilderFactory.get("scholarStep")
                .chunk(1)
                .reader(this.scholarItemReader())
                .processor(this.scholarItemProcessor())
                .writer(this.scholarItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Announcement> scholarItemWriter() throws Exception {
        JpaItemWriter itemWriter = new JpaItemWriterBuilder<Announcement>()
                .entityManagerFactory(entityManagerFactory)
                .build();
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    @StepScope
    public ItemProcessor<WebUrl, Announcement> scholarItemProcessor() throws Exception {
        return item -> {
            String fronturl = item.getFrontUrl();
            String backurl = item.getBackUrl();
            int lastid = item.getLastID();

            while(true) {
                String page = fronturl + lastid + backurl;
                lastid++;
                Document document = Jsoup.connect(page).get();
                String rawdata = document.select("[style=border-top:1px solid #B41C1B; border-bottom:1px solid #E7E7E7; background-color:#F5F5F5; clear:both; height:30px; padding:8px 7px 0 7px]").text();

                String title = document.select("[style=color:#b41c1b; font-size:12px; word-break:break-all;]").text();
                String date = rawdata.replace(title + " 관리자 ", "");
                date = date.substring(0, 14);

                if(title == "Null") {
                    lastid--;
                    item.setLastid(lastid);
                    break;
                }
                String sublink = page;

            }
        };
    }

    @Bean
    @StepScope
    public ItemReader<WebUrl> scholarItemReader() throws Exception {
        JpaCursorItemReader itemReader = new JpaCursorItemReaderBuilder<WebUrl>()
                .name("scholarItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select p from WebUrl")
                .build();
        itemReader.afterPropertiesSet();
        return itemReader;
    }
}
