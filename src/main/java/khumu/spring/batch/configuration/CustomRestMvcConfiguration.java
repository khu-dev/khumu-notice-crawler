package khumu.spring.batch.configuration;

import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

public class CustomRestMvcConfiguration {
    @Bean
    public RepositoryRestMvcAutoConfiguration repositoryRestMvcAutoConfiguration() {
        return new RepositoryRestMvcAutoConfiguration() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config)
        }
    }
}
