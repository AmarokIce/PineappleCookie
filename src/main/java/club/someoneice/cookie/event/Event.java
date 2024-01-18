package club.someoneice.cookie.event;

public abstract class Event {
    public boolean shouldCancel = false;

    public void setCancel() {
        if (!this.getClass().isAnnotationPresent(Cancelable.class))
            throw new IllegalCancelException(this.getClass().getSimpleName() + " cannot cancel but it will.");
        this.shouldCancel = true;
    }

    public enum Priority {
        HIGH,
        COMMON,
        LOW
    }
}
