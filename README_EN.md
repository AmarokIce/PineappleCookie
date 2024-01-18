![](img/PineappleCookie.png)

[简体中文](README.md) | [正體國語](README_TW.md) | English

# Pineapple Cookie
## What this?
The Pineapple Cookie is a personal and open Java Coding Lib,Code designed to simplify and decouple data access requirements in existing projects. <br />
So far, the following has been achieved:
- Data - A data storage structure based on HashMap, which provides some additional methods than using HashMap directly
- HashDataTable - Similar to Guava's HashTable, the meaning of which is to provide address mapping.
- EventBus - An event shuttle that can be plugged in and modified by routing and simply registering for priority. The disadvantage is that the data is a priority gray box when modified.
- Util - a variety of practical methods.

## Maven repositories and dependencies
Dependencies：
- Amarok Json4J
- Apache Log4J

Put to your Gradle:
```gradle
repositories {
    maven {
        allowInsecureProtocol = true
        url = "http://maven.snowlyicewolf.club/"
    }
}

dependencies {
    implementation "club.someoneice.cookie:PineappleCookie:${project. PineappleCookieVersion}"
}
```
## Practice
Data：
```java
public class DataTest {
    public static void main(String[] args) {
        // Common
        Data data0 = DataUtil.newData();
        data.putString("test", "Hello world!");
        
        // Lambda
        Data data1 = ObjectUtil.objectRun(DataUtil.newData, (it) -> {
           it.putString("test", "Hello world!");
           
            //  Get
            System.out.println(it.getString("test"));  // Hello world!
        });
    }
}
```

Event:
```java
// Create a Event
@Cancelable // Set Event Cancelable
public class ExampleEvent extends Event {
    // Create a Variable
    public int time;
    
    public ExampleEvent(int time) {
        this.time = time;
    }
    
    // Create a sub event. If the Event is Cancelable, the sub event will Cancelable too.
    public static class SubExampleEvent extends ExampleEvent {
        // Create a Value
        private final int data;
        
        public SubExampleEvent(int time, int data) {
            super(time);
            this.data = data;
        }
        
        public int getData() {
            return this.data;
        }
    }
}

// Subscribe Event
public class EventHelper {
    @SubEvent // MUST STATIC METHOD
    public static void doExampleEvent(ExampleEvent.SubExampleEvent event /* 此處訂閱子事件*/) {
        // Run your code.
        
        // If event Cancelable, we can setCancel.
        if (event.getData() == 0) event.setCancel();
    }
}

// Post Event
public class EventPoster {
    public static void main(String[] args) {
        // Registry the Event, Object Install or the Class.
        EventBus.register(EventHelper.class);
        
        // Other Code
        
        int time = 20;
        int data = 1;
        
        // Post event.
        ExampleEvent.SubExampleEvent event = EventBus.post(new ExampleEvent.SubExampleEvent(time, data));
        if (event.shouldCancel) return;
        this.time = event.time;
    }
}
```