package adam;

public record ProcessedLine(String category, String data) {
    /**
     * @param category The name of the line from the input
     * @param data     The String that accompanied the command in the input
     */
    public ProcessedLine(String category, String data) {
        this.category =normaliseCategory(category);
        this.data = data.strip();
    }

    public static String normaliseCategory(String rawCommand) {

        // 1. Convert the category to lowercase
        String normalisedCategory = rawCommand.toLowerCase();

        // 2. Remove leading and trailing whitespace
        normalisedCategory = normalisedCategory.replace(" ","");


        return normalisedCategory;
    }

    @Override
    public String toString() {
        return String.format("Category: %s, Data: %s", category, data);
    }

}
