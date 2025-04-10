package adam;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static adam.ApplicationConfigurationConstants.DEFAULT_NOW;
import static adam.ApplicationConfigurationConstants.DEFAULT_TIMEZONE;
import static adam.Messages.APPLICATION_SETTINGS;


public class Configuration {
    private static Configuration instance; // Singleton instance
    private boolean assignment_file_loading = true;
    private boolean assignment_item_processing = true;
    private String comment;
    private Boolean exit_message = false;
    private Boolean ignore_incomplete_assignments = false;
    private ZonedDateTime now = ApplicationConfigurationConstants.DEFAULT_NOW;
    private OutputType output_type = OutputType.SUMMARY;
    private Boolean show_comments = true;
    private Boolean show_config_status = false;
    private Boolean welcome_message = true;

    private ZoneId time_zone = ZoneId.of(DEFAULT_TIMEZONE);

    private String nowString = DEFAULT_NOW.toString();

    public Configuration() {

    }

    // Public method to get the instance
    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public ZoneId getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(ZoneId time_zone) {
        this.time_zone = time_zone;
    }

    public String getNowString(){return nowString;}

    public void setNowString(String nowString){this.nowString=nowString;}
    public Boolean getAssignment_file_loading() {
        return assignment_file_loading;
    }

    public Boolean getAssignment_item_processing() {
        return assignment_item_processing;
    }

    public void setAssignment_file_loading(Boolean assignment_file_loading) {
        this.assignment_file_loading = assignment_file_loading;
    }

    public void setAssignment_item_processing(Boolean assignment_item_processing) {
        this.assignment_item_processing = assignment_item_processing;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setExit_message(Boolean exit_message) {
        this.exit_message = exit_message;
    }

    public void setIgnore_incomplete_assignments(Boolean ignore_faulty_assignments) {
        this.ignore_incomplete_assignments = ignore_faulty_assignments;
    }

    public void setNow(ZonedDateTime now) {
        this.now = now.withZoneSameInstant(time_zone);
    }

    public void setOutput_type(OutputType output_type) {
        this.output_type = output_type;
    }

    public void setShow_comments(Boolean show_comments) {
        this.show_comments = show_comments;
    }

    public void setShow_config_status(Boolean show_config_status) {
        this.show_config_status = show_config_status;
    }

    public void setWelcome_message(Boolean welcome_message) {
        this.welcome_message = welcome_message;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getExit_message() {
        return exit_message;
    }

    public Boolean getIgnore_faulty_assignments() {
        return ignore_incomplete_assignments;
    }

    public ZonedDateTime getNow() {
        return now;
    }

    public OutputType getOutput_type() {
        return output_type;
    }

    public Boolean getShow_comments() {
        return show_comments;
    }

    public Boolean getShow_config_status() {
        return show_config_status;
    }

    public Boolean getWelcome_message() {
        return welcome_message;
    }

    public static List<String> allConfigCategorys() {
        List<String> strs = new ArrayList<>(List.of(
                "assignment_file_loading",
                "assignment_item_processing",
                "comment",
                "exit_message",
                "ignore_incomplete_assignments",
                "now",
                "output_type",
                "show_comments",
                "show_config_status",
                "welcome_message"
        ));

        for (int i = 0; i < strs.size(); i++) {
            strs.set(i, strs.get(i).toUpperCase());
        }

        return strs;
    }

    public void showSettings() {
        System.out.println(APPLICATION_SETTINGS);
        System.out.println("assignment_file_loading".toUpperCase()+": "+String.valueOf(assignment_file_loading).toUpperCase());
        System.out.println("assignment_item_processing".toUpperCase()+": "+String.valueOf(assignment_item_processing).toUpperCase());
        System.out.println("comment".toUpperCase()+": "+String.valueOf(true).toUpperCase());
        System.out.println("exit_message".toUpperCase()+": "+String.valueOf(exit_message).toUpperCase());
        System.out.println("ignore_incomplete_assignments".toUpperCase()+": "+String.valueOf(ignore_incomplete_assignments).toUpperCase());
        System.out.println("now".toUpperCase()+": "+nowString);
        System.out.println("output_type".toUpperCase()+": "+String.valueOf(output_type).toUpperCase());
        System.out.println("show_comments".toUpperCase()+": "+String.valueOf(show_comments).toUpperCase());
        System.out.println("show_config_status".toUpperCase()+": "+String.valueOf(show_config_status).toUpperCase());
        System.out.println("welcome_message".toUpperCase()+": "+String.valueOf(welcome_message).toUpperCase());
    }



}
