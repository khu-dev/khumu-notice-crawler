package khumu.spring.batch.publish;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import khumu.spring.batch.data.dto.AnnouncementDto;
import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.dto.NewAnnouncementCrawled;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventPublish {
    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    public void pubTopic() {

        SnsClient snsClient = SnsClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .build();

        ObjectMapper objectMapper = springMvcJacksonConverter.getObjectMapper();

        try {
            HashMap<String, MessageAttributeValue> hashMap = new HashMap<>();

            hashMap.put("resource_kind", MessageAttributeValue.builder()
                    .dataType("String")
                    .stringValue("announcement").build());
            hashMap.put("event_kind", MessageAttributeValue.builder()
                    .dataType("String")
                    .stringValue("create").build());

            List<String> stringList = new ArrayList<>();

            stringList.add("bo314");
            stringList.add("gusrl4025");

            PublishRequest request = PublishRequest.builder()
                    .message(objectMapper.writeValueAsString(NewAnnouncementCrawled.builder()
                                    .announcement(AnnouncementDto.builder()
                                            .title("2022학년도 공식 소개팅 자리 주선")
                                            .sub_link("abcccws.com")
                                            .date("2021-08-06")
                                            .author(AuthorDto.builder()
                                                    .id(1L)
                                                    .author_name("학교 공식 e 소개팅")
                                                    .followed(false).build()).build())
                                    .followers(stringList)
                                    .build()))
                    .topicArn("arn:aws:sns:ap-northeast-2:590304977225:khumu-messaging-local")
                    .messageAttributes(hashMap)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (SnsException e) {
            e.printStackTrace();
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

}
