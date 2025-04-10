package adam;

public enum Truthy{
    DISPLAY,
    ON,
    SHOW,
    TRUE,
    YES;

    /**
     * Checks if a given string matches any of the truthy values.
     * @param value The string to check.
     * @return true if the value is a truthy value, false otherwise.
     */
    public static boolean isTruthy(String value) {
        for (Truthy v : values()) {
            if (v.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
