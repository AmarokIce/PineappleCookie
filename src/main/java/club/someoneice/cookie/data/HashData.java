package club.someoneice.cookie.data;

import club.someoneice.cookie.util.ObjectUtil;
import club.someoneice.json.node.*;

import java.util.HashMap;
import java.util.Map;

public class HashData extends Data {
    public HashData() {
        super(new HashMap<>(), "NoNameTable");
    }

    public HashData(String name) {
        super(new HashMap<>(), name);
    }

    public HashMap<String, Object> getRawList() {
        Map<String, JsonNode<?>> map = mapNode.getObj();
        return ObjectUtil.objectLet(new HashMap<>(), (it) -> {
            map.forEach((key, value) -> it.put(key, value.getObj()));
        });
    }

    public MapNode getRawNode() {
        return this.mapNode;
    }

    public void put(Data value) {
        setData(value.name, value.getRawNode());
    }

    public void put(String key, Data value) {
        setData(key, value.getRawNode());
    }

    public void putString(String key, String value) {
        setData(key, new StringNode(value));
    }

    public void putInt(String key, int value) {
        setData(key, new IntegerNode(value));
    }

    public void putBoolean(String key, boolean value) {
        setData(key, new BooleanNode(value));
    }

    public void putDouble(String key, double value) {
        setData(key, new DoubleNode(value));
    }

    public void putFloat(String key, Float value) {
        setData(key, new FloatNode(value));
    }

    public Data get(String key) {
        return this.get(key, new HashData());
    }

    public Data get(String key, Data data) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.Map), (it) -> {
            if (it.getType() == JsonNode.NodeType.Null) return data;
            ((MapNode) it).getObj().forEach(data.mapNode::put);
            return data;
        });
    }

    public String getString(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.String), (it) -> it.getType() != JsonNode.NodeType.Null ? it.toString() : "null");
    }

    public int getInt(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.Int), (it) -> it.getType() != JsonNode.NodeType.Null ? (int) it.asTypeNode().getObj() : 0);
    }

    public boolean getBoolean(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.Boolean), (it) -> it.getType() != JsonNode.NodeType.Null && (boolean) it.asTypeNode().getObj());
    }

    public double getDouble(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.Double), (it) -> it.getType() != JsonNode.NodeType.Null ? (double) it.asTypeNode().getObj() : 0.0);
    }

    public float getFloat(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.Float), (it) -> it.getType() != JsonNode.NodeType.Null ? (float) it.asTypeNode().getObj() : 0.0f);
    }

    public boolean hasData(String key) {
        return mapNode.has(key);
    }

    public JsonNode.NodeType getDataType(String key) {
        if (!hasData(key)) return JsonNode.NodeType.Null;
        return mapNode.get(key).getType();
    }

    private <T extends JsonNode<?>> void setData(String key, T obj) {
        mapNode.put(key, obj);
    }

    private JsonNode<?> getData(String key, JsonNode.NodeType type) {
        if (getDataType(key) != type) return JsonNode.NULL;
        return mapNode.get(key);
    }
}
