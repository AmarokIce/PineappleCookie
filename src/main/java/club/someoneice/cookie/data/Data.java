package club.someoneice.cookie.data;

import java.util.Map;

import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;

public abstract class Data {
    public final String name;
    protected final MapNode mapNode = new MapNode();

    protected Data(Map<String, JsonNode<?>> map, String name) {
        this.name = name;
        map.forEach(this.mapNode::put);
    }

    abstract public MapNode getRawNode();
    abstract public Map<String, ?> getRawList();

    abstract void       put(Data value);
    abstract void       put(String key, Data value);
    abstract void       putString(String key, String value);
    abstract void       putInt(String key, int value);
    abstract void       putDouble(String key, double value);
    abstract void       putBoolean(String key, boolean value);

    abstract Data       get(String key);
    abstract Data       get(String key, Data data);
    abstract String     getString(String key);
    abstract int        getInt(String key);
    abstract double     getDouble(String key);
    abstract boolean    getBoolean(String key);
}
