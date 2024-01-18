package club.someoneice.cookie.config;

import club.someoneice.cookie.util.ConfigUtil;
import club.someoneice.cookie.util.DataUtil;
import club.someoneice.cookie.util.ObjectUtil;
import club.someoneice.json.JSON;
import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;
import club.someoneice.json.processor.Json5Builder;

import java.io.File;
import java.util.Map;

/**
 * Config Bean V2 - By Someoneice(AmarokIce)
 * @author Someoneice
 * */
@SuppressWarnings({"unchecked", "unused"})
public final class ConfigBean {
    private final Json5Builder.ObjectBean mapBean = ConfigUtil.INITIALIZE.getObjectBean();
    private final MapNode nodeBase;
    private final Map<String, Json5Builder.ObjectBean> nodeMapping = DataUtil.newHashMap();
    private final File file;

    public ConfigBean(String filePath) {
        this.file = new File(filePath + ".json5");

        nodeBase = ObjectUtil.objectLet(ConfigUtil.INITIALIZE.readFromJson(this.file), (it) -> it != null ? it : new MapNode());
    }

    /**
     * 当重载时使用。                      <br />
     * Use it when we should overload.
     */
    public void readFileAndOverrideConfig() {
        this.nodeBase.getObj().clear();

        mapBean.clean();
        nodeMapping.clear();

        MapNode nodeBase1 = ObjectUtil.objectLet(ConfigUtil.INITIALIZE.readFromJson(this.file), (it) -> it != null ? it : new MapNode());
        nodeBase1.getObj().forEach(nodeBase::put);
    }

    public String getString(String key, String defValue) {
        return getBean(key, defValue);
    }

    public int getInteger(String key, int defValue) {
        return getBean(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getBean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return getBean(key, defValue);
    }

    public double getFloat(String key, double defValue) {
        return getBean(key, defValue);
    }

    public String getString(String key, String defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public int getInteger(String key, int defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public boolean getBoolean(String key, boolean defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public float getFloat(String key, float defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public double getFloat(String key, double defValue, String pack) {
        return getBeanWithPackage(key, defValue, pack);
    }

    public void addNote(String note) {
        mapBean.addNote(note);
    }

    public void addEnter() {
        mapBean.enterLine();
    }

    public void addNote(String note, String packName) {
        Json5Builder.ObjectBean bean = nodeMapping.getOrDefault(packName, ConfigUtil.INITIALIZE.getObjectBean());
        bean.addNote(note);
        nodeMapping.put(packName, bean);
    }

    public void addEnter(String packName) {
        Json5Builder.ObjectBean bean = nodeMapping.getOrDefault(packName, ConfigUtil.INITIALIZE.getObjectBean());
        bean.enterLine();
        nodeMapping.put(packName, bean);
    }

    public void build() {
        Json5Builder builder = new Json5Builder();

        nodeMapping.forEach(mapBean::addBean);

        builder.put(mapBean);
        String str = builder.build();
        ConfigUtil.INITIALIZE.writeToJson(this.file, str);
    }

    private <T> T getBean(String key, T defValue) {
        T value;
        if (nodeBase.has(key)) {
            value = (T) nodeBase.get(key).getObj();
        } else value = defValue;

        mapBean.put(key, new JsonNode<>(value));
        return value;
    }

    private  <T> T getBeanWithPackage(String key, T defValue, String packName) {
        T value;
        Json5Builder.ObjectBean bean = nodeMapping.getOrDefault(packName, ConfigUtil.INITIALIZE.getObjectBean());
        if (nodeBase.has(packName)) {
            MapNode node = JSON.json.tryPullObjectOrEmpty(nodeBase.get(packName));

            if (node.has(key)) {
                value = (T) node.get(key).getObj();
            } else value = defValue;
        } else {
            value = defValue;
        }

        bean.put(key, new JsonNode<>(value));
        nodeMapping.put(packName, bean);

        return value;
    }
}
