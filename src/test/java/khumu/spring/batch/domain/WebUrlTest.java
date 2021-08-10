package khumu.spring.batch.domain;

import khumu.spring.batch.data.entity.WebUrl;
import org.junit.jupiter.api.Test;

public class WebUrlTest {
    @Test
    void test() {
        WebUrl webUrl = new WebUrl();

        webUrl.setWebUrl("예제테스트", "http:fff", "lastslsa", 98);
    }
}