package khumu.spring.batch.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {

    private final LocalDateTime localDateTime;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public DateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getDateTime() {
        return localDateTime.format(dateTimeFormatter);
    }
}
