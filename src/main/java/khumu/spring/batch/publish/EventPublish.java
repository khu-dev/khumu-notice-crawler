package khumu.spring.batch.publish;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import khumu.spring.batch.configuration.SnsConfiguration;
import khumu.spring.batch.data.dto.AnnouncementDto;
import khumu.spring.batch.data.dto.NewAnnouncementCrawled;
import khumu.spring.batch.data.dto.UserDto;
import khumu.spring.batch.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventPublish {
    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    @Autowired
    SnsConfiguration snsConfiguration;

    private final FollowService followService;

    public void pubTopic(AnnouncementDto announcementDto) {

        SnsClient snsClient = SnsClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .build();

        List<UserDto> userDtos = new ArrayList<>(followService.getUserByFollow(announcementDto.getAuthor().getAuthorName()));

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

            userDtos.forEach(c -> stringList.add(c.getUsername()));

            NewAnnouncementCrawled newAnnouncementCrawled = NewAnnouncementCrawled.builder()
                    .announcement(AnnouncementDto.builder()
                            .title(announcementDto.getTitle())
                            .subLink(announcementDto.getSubLink())
                            .date(announcementDto.getDate())
                            .author(announcementDto.getAuthor())
                            .build())
                    .followers(stringList)
                    .build();

            PublishRequest request = PublishRequest.builder()
                    .message(objectMapper.writeValueAsString(newAnnouncementCrawled))
                    .topicArn(snsConfiguration.getAddress()) // 설정파일로 빼서 사용 가능하도록 구축
                    .messageAttributes(hashMap)
                    .build();

            System.out.println(objectMapper.writeValueAsString(newAnnouncementCrawled));

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
