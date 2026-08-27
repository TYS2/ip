/** Represents an expected error caused by an invalid user command. */
public class BobException extends Exception {
    /** Creates an exception with a user-facing message. */
    public BobException(String message) {
        super(message);
    }
}