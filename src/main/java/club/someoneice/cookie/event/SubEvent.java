package club.someoneice.cookie.event;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubEvent {
    Event.Priority value() default Event.Priority.COMMON;
}
