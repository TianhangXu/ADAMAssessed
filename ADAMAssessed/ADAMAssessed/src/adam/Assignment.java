package adam;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

public record Assignment(String name, ZonedDateTime startDate, ZonedDateTime endDate, BigDecimal hoursRequired, Optional<String> course,Optional<Integer> points,Optional<String> comment) {

    // Method to calculate duration
    public Duration getDuration() {
        return Duration.between(startDate, endDate);
    }




}
