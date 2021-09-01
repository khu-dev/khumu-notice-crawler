package khumu.spring.batch.job;

import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.BoardRepository;
import khumu.spring.batch.tasklet.CsCrawling;
import khumu.spring.batch.tasklet.SWBoardCrawling;
import khumu.spring.batch.tasklet.ScholarCrawling;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
public class KhumuCrawlingConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private BoardRepository webUrlRepository;
    private AnnouncementRepository announcementRepository;

    public KhumuCrawlingConfiguration(JobBuilderFactory jobBuilderFactory,
                                      StepBuilderFactory stepBuilderFactory,
                                      EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public Job noticeUpdateJob() {
        return this.jobBuilderFactory.get("noticeUpdateJob")
                .incrementer(new RunIdIncrementer())
                .start(csCrawlingStep())
                .next(scholarCrawlingStep())
                .next(sWBoardCrawlingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step sWBoardCrawlingStep() {
        return this.stepBuilderFactory.get("sWBoardCrawlingStep")
                .tasklet(new CsCrawling(webUrlRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step csCrawlingStep() {
        return this.stepBuilderFactory.get("csCrawlingStep")
                .tasklet(new ScholarCrawling(webUrlRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step scholarCrawlingStep() {
        return this.stepBuilderFactory.get("scholarCrawlingStep")
                .tasklet(new SWBoardCrawling(webUrlRepository, announcementRepository))
                .build();
    }
}
