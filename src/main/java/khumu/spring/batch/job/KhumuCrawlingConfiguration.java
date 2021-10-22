package khumu.spring.batch.job;

import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.BoardRepository;
import khumu.spring.batch.tasklet.*;
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

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KhumuCrawlingConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BoardRepository boardRepository;
    private final AnnouncementRepository announcementRepository;
    private final AuthorRepository authorRepository;

    @Bean
    public Job noticeUpdateJob() {
        return this.jobBuilderFactory.get("noticeUpdateJob")
                .incrementer(new RunIdIncrementer())
                .start(csvReadingStep())
                .next(csCrawlingStep())
                .next(scholarCrawlingStep())
                .next(sWBoardCrawlingStep())
                .next(eECrawlingStep())
                .next(eInfoCrawlingStep())
                .next(artDesignCrawlingStep())
                .next(foreignLangCrawlingStep())
                .next(lINCCrawlingStep())
                .next(sWConCrawlingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step csvReadingStep() {
        return this.stepBuilderFactory.get("csvReadingStep")
                .tasklet(new CsvReading(boardRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step sWConCrawlingStep() {
        return this.stepBuilderFactory.get("sWConCrawlingStep")
                .tasklet(new SWConCrawling(boardRepository, authorRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step lINCCrawlingStep() {
        return this.stepBuilderFactory.get("lINCCrawlingStep")
                .tasklet(new LINCCrawling(boardRepository, authorRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step foreignLangCrawlingStep() {
        return this.stepBuilderFactory.get("foreignLangCrawlingStep")
                .tasklet(new ForeignLangCrawling(boardRepository, authorRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step artDesignCrawlingStep() {
        return this.stepBuilderFactory.get("artDesignCrawlingStep")
                .tasklet(new ArtDesignCrawling(boardRepository, authorRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step eInfoCrawlingStep() {
        return this.stepBuilderFactory.get("eInfoCrawlingStep")
                .tasklet(new EInfoCrawling(boardRepository, authorRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step eECrawlingStep() {
        return this.stepBuilderFactory.get("eECrawlingStep")
                .tasklet(new EECrawling(boardRepository, authorRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step sWBoardCrawlingStep() {
        return this.stepBuilderFactory.get("sWBoardCrawlingStep")
                .tasklet(new SWBoardCrawling(boardRepository, authorRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step scholarCrawlingStep() {
        return this.stepBuilderFactory.get("scholarCrawlingStep")
                .tasklet(new ScholarCrawling(boardRepository, authorRepository, announcementRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step csCrawlingStep() {
        return this.stepBuilderFactory.get("csCrawlingStep")
                .tasklet(new CsCrawling(boardRepository, authorRepository, announcementRepository))
                .build();
    }
}
