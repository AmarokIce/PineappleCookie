![](img/PineappleCookie.png)

简体中文 | [正體國語](README_TW.md) | [English](README_EN.md)

# 凤梨曲奇
## 这是什么
凤梨曲奇是一个个狼开放的Java通用编程库，旨在简化与解耦现有的项目中数据存取需求的代码。<br />
目前已经实现内容：
- Data - 基于HashMap的数据存储结构，相较于直接使用HashMap，提供了一些额外的方法
- HashDataTable - 类似Guava的HashTable，其意义在于提供地址映射。
- EventBus - 通过注解与简单注册进行分配优先级的事件班车，达到可插入式修改的目的。缺点是修改时数据为优先级灰盒。
- Util - 各种实用方法。

## Maven仓库与依赖
依赖：
- Amarok Json4J
- Apache Log4J

在Gradle中使用以下代码加入：
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
## 实践
Data：
```java
public class DataTest {
    public static void main(String[] args) {
        // 一般声明
        Data data0 = DataUtil.newData();
        data.putString("test", "Hello world!");
        
        // 函数式声明
        Data data1 = ObjectUtil.objectRun(DataUtil.newData, (it) -> {
           it.putString("test", "Hello world!");
           
           // 取出
            System.out.println(it.getString("test")); // Hello world!
        });
    }
}
```

Event:
```java
// 创建事件
@Cancelable // 设为可取消事件
public class ExampleEvent extends Event {
    // 事件可变成员
    public int time;
    
    public ExampleEvent(int time) {
        this.time = time;
    }
    
    // 声明子事件 - 事件的父事件可取消时，子事件将会继承。可取消事件不可以使用并发执行！
    public static class SubExampleEvent extends ExampleEvent {
        // 事件不可变成员
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

// 订阅事件
public class EventHelper {
    @SubEvent // 必须是Static方法
    public static void doExampleEvent(ExampleEvent.SubExampleEvent event /* 此处订阅子事件*/) {
        // 执行你的代码
        
        // 当事件是Cancelable，我们可以取消事件
        if (event.getData() == 0) event.setCancel();
    }
}

// 广播事件
public class EventPoster {
    public static void main(String[] args) {
        // 注册事件订阅者到总线，实例或是class
        EventBus.register(EventHelper.class);
        // 其他代码
        
        int time = 20;
        int data = 1;
        
        // 广播事件
        ExampleEvent.SubExampleEvent event = EventBus.post(new ExampleEvent.SubExampleEvent(time, data));
        if (event.shouldCancel) return;
        this.time = event.time;
    }
}
```