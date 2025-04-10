package adam;

import java.util.List;

import static adam.Messages.*;
import static adam.ProcessAssignmentCategory.createAssignment;
import static adam.ProcessConfigurationCategory.changeConfiguration;

public class ADAM {

  protected final String[] args;

  // You MUST NOT edit the signature of this constructor
  public ADAM(String[] args) {
    // You MAY edit the body of this constructor

    this.args = args;
  }


  // You MUST NOT edit the signature of this method
  public void run() {
    // You MUST edit the body of this constructor

    //Process Configuration
    List<String> configInput = BasicReadStream.read(System.in);

    List<ProcessedLine> processedConfig = Split.splitLines(configInput);
    changeConfiguration(processedConfig);
    Configuration config = Configuration.getInstance();

    boolean showConfig = config.getShow_config_status();
    if(showConfig){
      config.showSettings();
    }

    if(config.getWelcome_message()){
      System.out.println(WELCOME);
    }
    // Process Assignments
    Assignments assignments = Assignments.getInstance();
    List<List<ProcessedLine>> processedAssign = Split.processAssignmentsInput(args);

    for(List<ProcessedLine> input : processedAssign){
      Assignment assignment = createAssignment(input);
      if(assignment != null) {
        assignments.addAssignment(assignment);
      }
    }

    for(Assignment as:assignments.getAssignmentPool()){
      if(as.comment().isPresent()){
        System.out.printf(APPLICATION_COMMENT.toString()+"\n",as.comment().get());
      }
    }

    if(assignments.getAssignmentPool().isEmpty()){
      System.out.println(NO_ASSIGNMENTS);
    }

    if(config.getOutput_type() == OutputType.DAILY){
      assignments.showDaily();
    }else{
      assignments.showSummary();
    }

    if(config.getExit_message()){
      System.out.println(EXIT);
    }
  }
}
