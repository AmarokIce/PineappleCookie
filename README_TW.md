![](img/PineappleCookie.png)

[简体中文](README.md) | 正體國語 | [English](README_EN.md)

# 鳳梨麯奇
## 這是什麼
鳳梨麯奇是一個個狼開放的Java通用編程庫，旨在簡化與解耦現有的項目中數據存取需求的代碼。<br />
目前已經實現內容：
- Data - 基於HashMap的數據存儲結構，相較於直接使用HashMap，提供了一些額外的方法
- HashDataTable - 類似Guava的HashTable，其意義在於提供地址映射。
- EventBus - 通過註解與簡單註冊進行分配優先級的事件班車，達到可插入式修改的目的。缺點是修改時數據為優先級灰盒。
- Util - 各種實用方法。

## Maven倉庫與依賴
依賴：
- Amarok Json4J
- Apache Log4J

在Gradle中使用以下代碼加入：
```gradle
repositories {
    maven {
        allowInsecureProtocol = true
        url = "http://maven.snowlyicewolf.club/"
    }
}


dependencies {
    implementation "club.someoneice.cookie:PineappleCookie:${project.PineappleCookieVersion}"
}
```
## 實踐
Data：
```java
public class DataTest {
    public static void main(String[] args) {
        // 一般聲明
        Data data0 = DataUtil.newData();
        data.putString("test", "Hello world!");
        
        // 函數式聲明
        Data data1 = ObjectUtil.objectRun(DataUtil.newData, (it) -> {
           it.putString("test", "Hello world!");
           
            //  取出
            System.out.println(it.getString("test"));  // Hello world!
        });
    }
}
```

Event:
```java
// 創建事件
@Cancelable // 設為可取消事件
public class ExampleEvent extends Event {
    // 事件可變成員
    public int time;

    public ExampleEvent(int time) {
        this.time = time;
    }

    // 聲明子事件 - 事件的父事件可取消時，子事件將會繼承。 可取消事件不可以使用併發執行！
    public static class SubExampleEvent extends ExampleEvent {
        // 事件不可變成員
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

// 訂閱事件
public class EventHelper {
    @SubEvent // 必須是Static方法
    public static void doExampleEvent(ExampleEvent.SubExampleEvent event /* 此處訂閱子事件*/) {
        // 執行你的代碼

        // 當事件是Cancelable，我們可以取消事件
        if (event.getData() == 0) event.setCancel();
    }
}

// 廣播事件
public class EventPoster {
    public static void main(String[] args) {
        // 註冊事件訂閱者到總線，實例或是class
        EventBus.register(EventHelper.class);

        // 其他代碼

        int time = 20;
        int data = 1;

        // 廣播事件
        ExampleEvent.SubExampleEvent event = EventBus.post(new ExampleEvent.SubExampleEvent(time, data));
        if (event.shouldCancel) return;
        this.time = event.time;
    }
}
```