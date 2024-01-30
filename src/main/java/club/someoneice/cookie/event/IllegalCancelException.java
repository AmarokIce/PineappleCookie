package club.someoneice.cookie.event;

public class IllegalCancelException extends UnsupportedOperationException {
     public IllegalCancelException(String message) {
        super(message);
    }

    public IllegalCancelException(Class<? extends Event> clazz) {
        super("A illegal cancel in event :" + clazz.getSimpleName());
    }
}