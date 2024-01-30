package club.someoneice.cookie.table;

import club.someoneice.cookie.util.ObjectUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HashDataTable<X, Y, Z> {
    private final HashMap<X, HashMap<Y, Z>> table = new HashMap<>();

    public void put(X x, Y y, Z z) {
        table.put(x, ObjectUtil.objectRun(table.getOrDefault(x, new HashMap<>()), (it) -> {
            it.put(y, z);
        }));
    }

    public Z getOrDef(X x, Y y, Z def) {
        if (!containsKey(x, y)) return def;
        else return get(x, y);
    }

    public HashMap<Y, Z> getOrDef(X x, HashMap<Y, Z> def) {
        if (!containsKey(x)) return def;
        else return getRawMap(x);
    }

    public Z get(X x, Y y) {
        return ObjectUtil.objectDo(table.get(x), (it) -> it == null ? null : it.get(y));
    }

    public HashMap<Y, Z> getRawMap(X x) {
        return table.get(x);
    }

    public HashMap<X, HashMap<Y, Z>> getRawTable() {
        return this.table;
    }

    public HashDataTable<X, Y, Z> copy() {
        return ObjectUtil.objectRun(new HashDataTable<>(), (it) -> {
            it.table.putAll(this.table);
        });
    }

    public boolean containsKey(X x) {
        return this.table.containsKey(x);
    }

    public boolean containsKey(X x, Y y) {
        return this.table.containsKey(x) && this.table.get(x).containsKey(y);
    }

    public Set<X> keySet() {
        return table.keySet();
    }

    public Set<HashMap<Y, Z>> valueSet() {
        return new HashSet<>(table.values());
    }
}
