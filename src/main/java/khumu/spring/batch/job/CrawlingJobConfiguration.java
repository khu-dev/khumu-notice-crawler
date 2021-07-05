package khumu.spring.batch.job;

import khumu.spring.batch.dto.WebExtractive;
import khumu.spring.batch.dto.WebUrl;
import lombok.extern.slf4j.XSlf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class CrawlingJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    
    public CrawlingJobConfiguration(JobBuilderFactory jobBuilderFactory,
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
    private Step csBoardStep() {
        return this.stepBuilderFactory.get("csBoardStep")
                .chunk(1)
                .reader(this.csBoardItemReader())
                .processor(this.csBoarddItemProcessor())
                .writer(this.csBoardItemWriter())
                .build();
    }

    @Bean
    @StepScope
    private ItemWriter<WebExtractive> csBoardItemWriter() throws Exception {
        JpaItemWriter itemWriter = new JpaItemWriterBuilder<WebExtractive>()
                .entityManagerFactory(entityManagerFactory)
                .build();
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    @StepScope
    private ItemProcessor<WebUrl, WebExtractive> csBoarddItemProcessor() {
        return item -> new WebExtractive(){
            String target = item.getUrl();
            Document connectcheck;
            try {
                connectcheck = Jsoup.connect(target + "1").get();
            } catch (IOException e) {
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
    private ItemReader<WebUrl> csBoardItemReader() throws Exception {
        JpaCursorItemReader itemReader = new JpaCursorItemReaderBuilder<WebUrl>()
                .name("csBoardItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select p from Weburl")
                .build();
        itemReader.afterPropertiesSet();
        return itemReader;
    }
}
