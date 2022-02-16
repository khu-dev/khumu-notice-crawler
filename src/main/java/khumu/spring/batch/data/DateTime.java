package khumu.spring.batch.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTime {

    private final LocalDate localDate;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DateTime(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getDateTime() {
        return localDate.format(dateTimeFormatter);
    }
}
