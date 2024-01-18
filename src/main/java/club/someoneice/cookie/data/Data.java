package club.someoneice.cookie.data;

import club.someoneice.cookie.util.DataUtil;
import club.someoneice.cookie.util.ObjectUtil;
import club.someoneice.json.node.*;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Data {
    public final String name;
    private final MapNode mapNode = new MapNode();

    /* ====== INSTANCE ====== */
    public Data(String name) {
        this.name = name;
    }

    public Data(MapNode node, String name) {
        this(name); node.getObj().forEach(this.mapNode::put);
    }

    public Data(Map<String, JsonNode<?>> map, String name) {
        this(name); map.forEach(this.mapNode::put);
    }

    public Data() {
        this("NoNameTable");
    }

    public Data(MapNode node) {
        this(); node.getObj().forEach(this.mapNode::put);
    }

    public Data(Map<String, JsonNode<?>> map) {
        this(); map.forEach(this.mapNode::put);
    }

    /* ====== Data Tools ====== */

    public LinkedHashMap<String, Object> getRawList() {
        Map<String, JsonNode<?>> map = mapNode.getObj();
        return ObjectUtil.objectLet(new LinkedHashMap<>(), (it) -> {
            map.forEach((key, value) -> it.put(key, value.getObj()));
        });
    }

    public MapNode getRawNode() {
        return this.mapNode;
    }

    /* ====== Put and get ====== */
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

    public void putDouble(String key, Double value) {
        setData(key, new DoubleNode(value));
    }

    public void putFloat(String key, Float value) {
        setData(key, new FloatNode(value));
    }

    public Data get(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.Map), (it) -> {
            if (it.getType() == JsonNode.NodeType.Null) return DataUtil.newData();
            Data data = DataUtil.newData();
            ((MapNode) it).getObj().forEach(data.mapNode::put);
            return data;
        });
    }

    public String getString(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.String), (it) -> it.getType() != JsonNode.NodeType.Null ? it.toString() : "null");
    }

    public int putInt(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.Int), (it) -> it.getType() != JsonNode.NodeType.Null ? (int) it.asTypeNode().getObj() : 0);
    }

    public boolean putBoolean(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.Boolean), (it) -> it.getType() != JsonNode.NodeType.Null && (boolean) it.asTypeNode().getObj());
    }

    public double putDouble(String key) {
        return ObjectUtil.objectDo(getData(key, JsonNode.NodeType.Double), (it) -> it.getType() != JsonNode.NodeType.Null ? (double) it.asTypeNode().getObj() : 0.0);
    }

    public float putFloat(String key) {
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
