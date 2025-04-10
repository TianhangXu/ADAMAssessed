package adam;

import java.math.BigDecimal;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static adam.Messages.*;


public class ProcessAssignmentCategory {
    public static Assignment createAssignment(List<ProcessedLine> input){
        String fileName = null;

        String name = null;

        ZonedDateTime startDate = null;

        ZonedDateTime endDate = null;

        BigDecimal hoursRequired = null;

        Optional<String> course = Optional.empty();

        Optional<String> comment = Optional.empty();

        Optional<Integer> points = Optional.empty();

        Assignment assignment = null;

        Configuration config = Configuration.getInstance();

        int lineNumber = -1;
        for(ProcessedLine line:input){
            lineNumber += 1;
            String wrongCommand = null;
            String category = line.category();
            String data = line.data();

            switch(category){
                case "name":
                    name = data;
                    break;
                case "course":
                    course = Optional.of(data);
                    break;
                case "start":
                    startDate = ZonedDateTime.parse(data);
                    break;
                case "end":
                    endDate = ZonedDateTime.parse(data);
                    break;
                case "hours":
                    hoursRequired = new BigDecimal(data);
                    break;
                case "points":
                    points = Optional.of(Integer.parseInt(data));
                    break;
                case "comment":
                    comment = Optional.of(data);
                    break;
                case "filename":
                    fileName = data;
                    if(config.getAssignment_file_loading()){
                        System.out.println(String.format(LOADING_ASSIGNMENT.toString(),fileName));
                    }
                    break;
                default:
                    wrongCommand = category.toUpperCase();
            }
            if(config.getAssignment_item_processing()){
                if(lineNumber > 0) {
                    String message = String.format("ConfigurationItem[filename=%s, command=%s, data=%s, lineNumber=%d]", fileName, category.toUpperCase(), data, lineNumber);
                    String message2 = String.format(READ_CONFIGURATION_ITEM.toString(), message);
                    System.out.println(message2);
                }
                if(wrongCommand != null){
                    System.out.println(String.format(UNKNOWN_ASSIGNMENT_COMMAND.toString(),wrongCommand));
                    System.out.println(String.format(VALID_POSSIBILITIES.toString(),"[COMMENT, COURSE, END, HOURS, NAME, POINTS, START]"));
                }
            }

        }

        List<String> missingItems = getMissing(name,startDate,endDate,hoursRequired);
        if(missingItems.isEmpty()){
            assignment = new Assignment(name,startDate,endDate,hoursRequired,course,points,comment);
        } else{
            for(String command:missingItems){
                System.out.println(String.format(MISSING_ASSIGNMENT_ITEM.toString(),fileName,command));
            }
        }

        return assignment;

    }

    public static List<String> getMissing(String name,ZonedDateTime startDate,ZonedDateTime endDate,BigDecimal hoursRequired){
        List<String> missingItems = new ArrayList<>();
        if(name == null){
            missingItems.add("NAME");
        }else if(startDate == null){
            missingItems.add("START");
        }else if(endDate == null){
            missingItems.add("END");
        }else if(hoursRequired == null){
            missingItems.add("HOURS");
        }
        return missingItems;
    }

    public static void showComment(List<ProcessedLine> processedLines){
        Configuration config = Configuration.getInstance();
        String comment = null;
        if(config.getShow_comments()){
            for(ProcessedLine pl : processedLines){
                if(pl.category().equals("comment")){
                    comment = pl.data();
                }
            }
            if(comment != null) System.out.printf(APPLICATION_COMMENT.toString()+"\n",comment);
        }
    }

}

