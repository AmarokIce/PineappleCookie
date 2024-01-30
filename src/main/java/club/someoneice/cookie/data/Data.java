package club.someoneice.cookie.data;

import java.util.Map;

import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;

public abstract class Data {
    public final String name;
    protected final MapNode mapNode;

    protected Data(Map<String, JsonNode<?>> map, String name) {
        this.name = name;
        this.mapNode = new MapNode(map);
    }

    public MapNode getRawNode() {
        return this.mapNode;
    }

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
