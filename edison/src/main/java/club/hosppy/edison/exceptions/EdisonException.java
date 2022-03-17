package club.hosppy.edison.exceptions;

public class EdisonException extends RuntimeException {

    public EdisonException(String message) {
        super(message);
    }

    public EdisonException(String message, Throwable cause) {
        super(message, cause);
    }
}
