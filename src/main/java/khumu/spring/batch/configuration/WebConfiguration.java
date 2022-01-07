package khumu.spring.batch.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS 설정
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")              //와일드카드 경로 설정
                .allowedOrigins("*")            //자원 공유 허락 origin 설정
                .allowedMethods("GET", "POST")  //허용할 HTTP method 설정
                .maxAge(3000);                  //pre-flight 리퀘스트 캐싱 시간 설정
    }
}
