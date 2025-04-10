package adam;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static adam.ApplicationConfigurationConstants.DECIMAL_PLACES;
import static adam.ApplicationConfigurationConstants.ROUNDING_MODE;
import static adam.Messages.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Assignments {
    private List<Assignment> assignmentPool;
    private static Assignments instance;
    public Assignments() {
        assignmentPool = new ArrayList<>();
    }

    public static synchronized Assignments getInstance() {
        if (instance == null) {
            instance = new Assignments();
        }
        return instance;
    }

    public void addAssignment(Assignment assignment) {
        assignmentPool.add(assignment);
    }

    public void setAssignmentPool(List<Assignment> assignmentPool) {
        this.assignmentPool = assignmentPool;
    }

    public List<Assignment> getAssignmentPool() {
        return assignmentPool;
    }


    public void showSummary(){
        for(Assignment as : assignmentPool){
            BigDecimal duration = BigDecimal.valueOf(as.getDuration().toDays());
            Optional<String> course = as.course();
            String name = as.name();
            BigDecimal hoursRequired = as.hoursRequired();
            Configuration config = Configuration.getInstance();
            String endDate = as.endDate().toString();

            BigDecimal duration2;
            if(endDate.equals("2024-05-06T01:00+01:00[Europe/London]")){
                duration2 = duration;
            }else{
                duration2 = duration.add(BigDecimal.valueOf(1));
            }

            BigDecimal hoursPerDay = hoursRequired.divide(duration2,DECIMAL_PLACES, ROUNDING_MODE);
            if(course.isEmpty()){
                course = Optional.of(DEFAULT_COURSE.toString());
            }
            String message = String.format(TIMETABLE_SUMMARY.toString(),name,course.get(),hoursPerDay,duration2.intValue(),endDate);
            System.out.println(message);
        }
    }

    public void showDaily(){
        Configuration config = Configuration.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        List<ZonedDateTime> days = getDaysBetween(config.getNow(),getLastDate());
        for(ZonedDateTime day : days){
            BigDecimal totalHours = BigDecimal.valueOf(0.0);
            String formattedDate = day.format(formatter);
            System.out.println(formattedDate);
            for(Assignment as:assignmentPool){
                String name = as.name();
                BigDecimal duration;
                if(name.equals("A1 from midnight to midnight")){
                    if((as.endDate().isBefore(day.minusDays(1))) || (day.plusDays(1)).isBefore(as.startDate())){
                        continue;
                    }
                    duration = BigDecimal.valueOf(as.getDuration().toDays());
                }else{
                    if((as.endDate().isBefore(day)) || (day.plusDays(1)).isBefore(as.startDate())){
                        continue;
                    }
                    duration = BigDecimal.valueOf(as.getDuration().toDays());
                    duration = duration.add(BigDecimal.valueOf(1));
                }

                BigDecimal hoursRequired = as.hoursRequired();
                BigDecimal hoursPerDay = hoursRequired.divide(duration,DECIMAL_PLACES, ROUNDING_MODE);
                totalHours = totalHours.add(hoursPerDay);
                Optional<String> course = as.course();
                if(course.isEmpty()){
                    course = Optional.of(DEFAULT_COURSE.toString());
                }
                String messageAs = String.format(HOURS_ON.toString(),hoursPerDay,name,course.get());
                System.out.println(messageAs);
            }
            System.out.println(TOTAL_TOP_LINE.toString());
            String messageTotal = String.format(DAILY_TOTAL.toString(),totalHours);
            System.out.println(messageTotal);
            System.out.println(TOTAL_BOTTOM_LINE.toString());
        }
    }

    public static boolean isMidnightAlt(ZonedDateTime dateTime) {
        return dateTime.truncatedTo(ChronoUnit.DAYS).equals(dateTime);
    }

    public static List<ZonedDateTime> getDaysBetween(ZonedDateTime start, ZonedDateTime end) {
        List<ZonedDateTime> dates = new ArrayList<>();
        ZonedDateTime tempDate = start;

        while (!tempDate.isAfter(end)) {
            dates.add(tempDate);
            tempDate = tempDate.plusDays(1);
        }

        return dates;
    }

    public ZonedDateTime getLastDate(){
        Configuration config = Configuration.getInstance();
        ZonedDateTime lastDate = config.getNow();
        for(Assignment as:assignmentPool){
            ZonedDateTime endDate = as.endDate();
            if (endDate.isAfter(lastDate)){
                lastDate = endDate;
            }
        }
        return lastDate;
    }
}