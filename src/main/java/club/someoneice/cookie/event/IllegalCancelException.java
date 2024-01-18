package club.someoneice.cookie.event;

public class IllegalCancelException extends UnsupportedOperationException {
    public IllegalCancelException() {
        super();
    }

     public IllegalCancelException(String message) {
        super(message);
    }

    public IllegalCancelException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCancelException(Throwable cause) {
        super(cause);
    }
}