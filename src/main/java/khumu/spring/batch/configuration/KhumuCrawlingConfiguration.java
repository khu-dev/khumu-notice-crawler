package khumu.spring.batch.configuration;

import khumu.spring.batch.publish.EventPublish;
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
    private final EventPublish eventPublish;

    @Bean
    public Job noticeUpdateJob() {
        return this.jobBuilderFactory.get("noticeUpdateJob")
//                .incrementer(new RunIdIncrementer())
                .start(artDesignCrawlingStep())
                .next(csCrawlingStep())
                .next(csCommonCrawlingStep())
                .next(csCareerCrawlingStep())
                .next(eECrawlingStep())
                .next(eECommonCrawlingStep())
                .next(eECareerCrawlingStep())
                .next(eEExtraCrawlingStep())
                .next(foreignLangCrawlingStep())
                .next(foreignLangCommonCrawlingStep())
                .next(foreignLangCareerCrawlingStep())
                .next(lINCCrawlingStep())
                .next(scholarCrawlingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step lINCCrawlingStep() {
        return this.stepBuilderFactory.get("lINCCrawlingStep")
                .tasklet(new LINCCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step foreignLangCrawlingStep() {
        return this.stepBuilderFactory.get("foreignLangCrawlingStep")
                .tasklet(new ForeignLangCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step artDesignCrawlingStep() {
        return this.stepBuilderFactory.get("artDesignCrawlingStep")
                .tasklet(new ArtDesignCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step eECrawlingStep() {
        return this.stepBuilderFactory.get("eECrawlingStep")
                .tasklet(new EECrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step scholarCrawlingStep() {
        return this.stepBuilderFactory.get("scholarCrawlingStep")
                .tasklet(new ScholarCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step csCrawlingStep() {
        return this.stepBuilderFactory.get("csCrawlingStep")
                .tasklet(new CsCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step foreignLangCommonCrawlingStep() {
        return this.stepBuilderFactory.get("foreignLangCommonCrawlingStep")
                .tasklet(new ForeignLangCommonCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step foreignLangCareerCrawlingStep() {
        return this.stepBuilderFactory.get("foreignLangCareerCrawlingStep")
                .tasklet(new ForeignLangCareerCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step csCareerCrawlingStep() {
        return this.stepBuilderFactory.get("csCareerCrawlingStep")
                .tasklet(new CsCareerCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step csCommonCrawlingStep() {
        return this.stepBuilderFactory.get("csCommonCrawlingStep")
                .tasklet(new CsCommonCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step eECommonCrawlingStep() {
        return this.stepBuilderFactory.get("eECommonCrawlingStep")
                .tasklet(new EECommonCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step eECareerCrawlingStep() {
        return this.stepBuilderFactory.get("eECareerCrawlingStep")
                .tasklet(new EECareerCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }

    @Bean
    @JobScope
    public Step eEExtraCrawlingStep() {
        return this.stepBuilderFactory.get("eEExtraCrawlingStep")
                .tasklet(new EEExtraCrawling(boardRepository, authorRepository, announcementRepository, eventPublish))
                .build();
    }
}
