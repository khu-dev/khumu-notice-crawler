package khumu.spring.batch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConstructorBinding
@ConfigurationProperties("sns")
public class SnsConfiguration {
    private String address;
}
