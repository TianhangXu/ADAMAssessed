package adam;

import java.time.ZonedDateTime;
import java.util.List;

import static adam.OutputType.isValidOutputType;
import static adam.Truthy.isTruthy;



public class ProcessConfigurationCategory {

    public static void changeConfiguration(List<ProcessedLine> processedLines){
        Configuration config = Configuration.getInstance();
        boolean overwrtting = false;
        for(ProcessedLine pl : processedLines){
            String category = pl.category();

            String data = pl.data();

            String message;
            boolean bool;
            String category2 = category;
            category = category.replace("_","");
            switch(category){
                case "assignmentfileloading":
                    bool = isTruthy(data);
                    config.setAssignment_file_loading(bool);

                    message = String.format(Messages.SETTING_APP_CONFIG_ITEM.toString(), "ASSIGNMENT_FILE_LOADING",String.valueOf(bool).toUpperCase());
                    System.out.println(message);
                    break;
                case "assignmentitemprocessing":
                    bool = isTruthy(data);
                    config.setAssignment_item_processing(bool);
                    message = String.format(Messages.SETTING_APP_CONFIG_ITEM.toString(), "ASSIGNMENT_ITEM_PROCESSING",String.valueOf(bool).toUpperCase());
                    System.out.println(message);
                    break;
                case "comment":
                    message = data;
                    if(config.getShow_comments()){
                        System.out.println(message);
                    }

                    break;

                case "ignoreincompleteassignments":
                    bool = isTruthy(data);
                    config.setIgnore_incomplete_assignments(bool);
                    message = String.format(Messages.SETTING_APP_CONFIG_ITEM.toString(), "IGNORE_INCOMPLETE_ASSIGNMENTS",String.valueOf(bool).toUpperCase());
                    System.out.println(message);
                    break;
                case "now":
                    config.setNow(ZonedDateTime.parse(data));
                    config.setNowString(data);
                    config.setTime_zone(ZonedDateTime.parse(data).getZone());
                    message = String.format(Messages.SETTING_APP_CONFIG_ITEM.toString(), "NOW",data);
                    System.out.println(message);
                    break;
                case "outputtype":
                    if(isValidOutputType(data)){
                        OutputType type = OutputType.valueOf(data.toUpperCase());
                        config.setOutput_type(type);
                        message = String.format(Messages.SETTING_APP_CONFIG_ITEM.toString(), "OUTPUT_TYPE",data.toUpperCase());
                    }else{
                        message = String.format(Messages.INVALID_SUMMARY_TYPE.toString(), "OUTPUT_TYPE", OutputType.getNames());
                    }
                    System.out.println(message);
                    break;
                case "showcomments":
                    bool = isTruthy(data);
                    if(overwrtting){
                        boolean previous = config.getShow_comments();
                        config.setShow_comments(bool);
                        message = String.format(Messages.OVERWRITING_APP_CONFIG_ITEM.toString(), "COMMENTS",String.valueOf(previous).toUpperCase(),String.valueOf(bool).toUpperCase());
                    }else{
                        config.setShow_comments(bool);
                        message = String.format(Messages.SETTING_APP_CONFIG_ITEM.toString(), "COMMENTS",String.valueOf(bool).toUpperCase());
                        overwrtting = true;
                    }

                    System.out.println(message);
                    break;
                case "showconfigstatus":
                    bool = isTruthy(data);
                    config.setShow_config_status(bool);
                    message = String.format(Messages.SETTING_APP_CONFIG_ITEM.toString(), "CONFIG_STATUS",String.valueOf(bool).toUpperCase());
                    System.out.println(message);
                    break;
                case "welcomemessage":
                    bool = isTruthy(data);
                    config.setWelcome_message(bool);
                    message = String.format(Messages.SETTING_APP_CONFIG_ITEM.toString(), "WELCOME_MESSAGE",String.valueOf(bool).toUpperCase());
                    System.out.println(message);
                    break;
                case "exitmessage":
                    bool = isTruthy(data);
                    config.setExit_message(bool);
                    message = String.format(Messages.SETTING_APP_CONFIG_ITEM.toString(), "EXIT_MESSAGE",String.valueOf(bool).toUpperCase());
                    System.out.println(message);
                    break;
                default:
                    message = String.format(Messages.INVALID_APP_CONFIG_ITEM.toString(), category2.toUpperCase()) + "\n" + String.format(Messages.VALID_POSSIBILITIES.toString(),Configuration.allConfigCategorys());
                    System.out.println(message);
            }

        }

    }
}
