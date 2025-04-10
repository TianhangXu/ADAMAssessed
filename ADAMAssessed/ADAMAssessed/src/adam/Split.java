package adam;

import adam.Configuration;
import adam.ProcessedLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Split{

    public static List<List<ProcessedLine>> processAssignmentsInput(String[] args){
        List<List<ProcessedLine>> processedLines = new ArrayList<>(args.length);
        for(String filePath:args){
            File file = new File(filePath);
            String fileName = file.getName();
            Configuration config = Configuration.getInstance();


            List<String> fileContents = readAllLinesFromFile(filePath);
            processedLines.add(splitAssignmentLines(fileContents,fileName));
        }

        return processedLines;
    }
    public static List<ProcessedLine> splitLines(List<String> input){
        List<ProcessedLine> processedLines = new ArrayList<>(input.size());


        for (String line : input) {
            // Skip empty lines
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split("\t", 2); // Split only on the first occurrence
            if (parts.length == 2) {
                processedLines.add(new ProcessedLine(parts[0], parts[1]));
            } else if (parts.length == 1) {
                processedLines.add(new ProcessedLine(parts[0], ""));
            }
        }


        // Note: it is safe to return splitLines because it is a local variable
        return processedLines;
    }

    public static List<String> readAllLinesFromFile(String filePath){
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            // Handle exception, e.g., log it or return an empty list
            System.err.println("Failed to read file: " + filePath);
            return Collections.emptyList();  // Return an empty list on failure
        }
    }

    public static String removeFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return filename.substring(0, lastDotIndex);
        }
        return filename;
    }

    public static List<ProcessedLine> splitAssignmentLines(List<String> input,String fileName){
        List<ProcessedLine> processedLines = new ArrayList<>(input.size());
        processedLines.add(new ProcessedLine("filename", fileName));

        for (String line : input) {

            // Skip empty lines
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split("\t", 2); // Split only on the first occurrence
            if (parts.length == 2) {
                ProcessedLine pl = new ProcessedLine(parts[0], parts[1]);
                processedLines.add(pl);
            } else if (parts.length == 1) {
                processedLines.add(new ProcessedLine(parts[0], ""));
            }
        }
        // Note: it is safe to return splitLines because it is a local variable
        return processedLines;
    }


}
