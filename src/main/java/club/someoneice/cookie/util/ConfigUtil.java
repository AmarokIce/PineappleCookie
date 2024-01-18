package club.someoneice.cookie.util;

import club.someoneice.cookie.Info;
import club.someoneice.json.JSON;
import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;
import club.someoneice.json.processor.Json5Builder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class ConfigUtil {
    private ConfigUtil() {}

    public static final ConfigUtil INITIALIZE = new ConfigUtil();

    public MapNode readFromJson(File file) {
        try {
            if (!file.exists() || !file.isFile()) FileUtil.tryCreateFile(file);
            else if (file.canRead()) {
                return (MapNode) ObjectUtil.objectDo(JSON.json5.parse(file), (it) -> {
                    if (it.getType() == JsonNode.NodeType.Map) return it;
                    return new MapNode();
                });
            }

            return new MapNode();
        } catch (IOException e) {
            return null;
        }
    }

    public void writeToJson(File file, String str)  {
        try {
            if (!file.exists() || !file.isFile()) FileUtil.tryCreateFile(file);
            Files.write(file.toPath(), str.getBytes());
        } catch (IOException e) {
            Info.LOG.error(e);
        }
    }

    public Json5Builder.ObjectBean getObjectBean() {
        return new Json5Builder.ObjectBean();
    }

    public Json5Builder.ArrayBean getArrayBean() {
        return new Json5Builder.ArrayBean();
    }
}
