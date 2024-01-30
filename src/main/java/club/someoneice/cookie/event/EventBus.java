package club.someoneice.cookie.event;

import club.someoneice.cookie.Info;
import club.someoneice.cookie.table.HashDataTable;
import club.someoneice.cookie.util.ObjectUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class EventBus {
    private static HashDataTable<Class<? extends Event>, Event.Priority, ArrayList<Method>> events = new HashDataTable<>();

    public static <T extends Event> T post(T event) {
        if (!events.containsKey(event.getClass())) return event;
        Map<Event.Priority, ArrayList<Method>> data = events.getRawMap(event.getClass());
        try {
            for (Method method : data.get(Event.Priority.HIGH)) {
                method.invoke(event);
                if (event.shouldCancel) return event;
            }

            for (Method method : data.get(Event.Priority.COMMON)) {
                method.invoke(event);
                if (event.shouldCancel) return event;
            }

            for (Method method : data.get(Event.Priority.LOW)) {
                method.invoke(event);
                if (event.shouldCancel) return event;
            }
        } catch (Exception e) {
            Info.LOG.error(e);
        }

        return event;
    }

    @SuppressWarnings("deprecation")
    public static <T extends Event> T post(Class<T> eventClazz) {
        try {
            T event = eventClazz.newInstance();
            return post(event);
        } catch (Exception e) {
            Info.LOG.error("Cannot instance the event class : {}", eventClazz.getSimpleName());
            return null;
        }
    }

    public static void register(Object obj) {
        register(obj.getClass());
    }

    @SuppressWarnings("unchecked")
    public static void register(Class<?> obj) {
        for (Method method : obj.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(SubEvent.class)) continue;
            Class<?> clazz = method.getParameterTypes()[0];
            if (!clazz.isAssignableFrom(Event.class)) return;
            Event.Priority priority = method.getDeclaredAnnotation(SubEvent.class).value();
            events.put((Class<? extends Event>) clazz, priority, ObjectUtil.objectRun(
                    events.getOrDef((Class<? extends Event>) clazz, priority, new ArrayList<>()),
                    (it) -> { it.add(method); }));
        }
    }
}
