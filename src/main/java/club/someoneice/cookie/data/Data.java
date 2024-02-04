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

    abstract public void       put(Data value);
    abstract public void       put(String key, Data value);
    abstract public void       putString(String key, String value);
    abstract public void       putInt(String key, int value);
    abstract public void       putDouble(String key, double value);
    abstract public void       putBoolean(String key, boolean value);

    abstract public Data       get(String key);
    abstract public Data       get(String key, Data data);
    abstract public String     getString(String key);
    abstract public int        getInt(String key);
    abstract public double     getDouble(String key);
    abstract public boolean    getBoolean(String key);
}
