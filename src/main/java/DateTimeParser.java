import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/** Parses date-time values supplied by users. */
public final class DateTimeParser {
    private static final List<DateTimeFormatter> INPUT_FORMATS = List.of(
            DateTimeFormatter.ofPattern("uuuu-MM-dd"),
            DateTimeFormatter.ofPattern("d/M/uuuu")
    );

    private DateTimeParser() {
        // Utility class; do not instantiate.
    }

    /**
     * Parses a supported date-time value.
     *
     * @param input date and time entered by the user
     * @return parsed date and time
     * @throws BobException if the input has an unsupported or invalid format
     */
    public static LocalDateTime parse(String input) throws BobException {
        for (DateTimeFormatter formatter : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(input.trim(), formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next supported format.
            }
        }

        throw new BobException(
                "Please enter the date as yyyy-MM-dd HHmm, "
                        + "for example 2019-12-02 1800.");
    }
}