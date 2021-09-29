package khumu.spring.batch.job;

import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.BoardRepository;
import khumu.spring.batch.tasklet.CsCrawling;
import khumu.spring.batch.tasklet.SWBoardCrawling;
import khumu.spring.batch.tasklet.ScholarCrawling;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class KhumuCrawlingConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BoardRepository boardRepository;
    private final AnnouncementRepository announcementRepository;
    private final EntityManagerFactory entityManagerFactory;

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
                .tasklet(new SWBoardCrawling(boardRepository, announcementRepository, entityManagerFactory))
                .build();
    }
    @Bean
    @JobScope
    public Step scholarCrawlingStep() {
        return this.stepBuilderFactory.get("scholarCrawlingStep")
                .tasklet(new ScholarCrawling(boardRepository, announcementRepository, entityManagerFactory))
                .build();
    }
    @Bean
    @JobScope
    public Step csCrawlingStep() {
        return this.stepBuilderFactory.get("csCrawlingStep")
                .tasklet(new CsCrawling(boardRepository, announcementRepository, entityManagerFactory))
                .build();
    }
}
