package adam;

import java.util.ArrayList;
import java.util.List;

public enum OutputType {
    SUMMARY,DAILY;

    public static List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (OutputType type : OutputType.values()) {
            names.add(type.name());
        }
        return names;
    }
    public static boolean isValidOutputType(String name) {
        for (OutputType type : OutputType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
