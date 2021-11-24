package khumu.spring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableBatchProcessing
public class KhumuNoticeCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhumuNoticeCrawlerApplication.class, args);
    }

}
