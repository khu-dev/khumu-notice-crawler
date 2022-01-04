package khumu.spring.batch;

import khumu.spring.batch.configuration.SnsConfiguration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableBatchProcessing
@EnableConfigurationProperties(SnsConfiguration.class)
public class KhumuNoticeCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhumuNoticeCrawlerApplication.class, args);
    }

}