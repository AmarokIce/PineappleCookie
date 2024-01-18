package club.someoneice.cookie.util;

import club.someoneice.cookie.data.Data;
import club.someoneice.cookie.data.HashDataTable;
import club.someoneice.json.Pair;

import java.util.*;

public final class DataUtil {
    private DataUtil() {}

    public static <T> ArrayList<T> newArrayList(T ... e) {
        return ObjectUtil.objectRun(new ArrayList<>(), (it) -> {
            it.addAll(Arrays.asList(e));
        });
    }

    public static <T> ArrayList<T> newArrayList(Collection<T> e) {
        return new ArrayList<>(e);
    }

    public static <T> HashSet<T> newHashSet(T ... e) {
        return ObjectUtil.objectRun(new HashSet<>(), (it) -> {
            it.addAll(Arrays.asList(e));
        });
    }

    public static <T> HashSet<T> newHashSet(Collection<T> e) {
        return new HashSet<>(e);
    }

    @SafeVarargs
    public static <K, V> HashMap<K, V> newHashMap(Pair<K, V>... pairs) {
        return ObjectUtil.objectRun(new HashMap<>(), (it) -> {
            Arrays.stream(pairs).forEach((p) -> {
                it.put(p.getKey(), p.getValue());
            });
        });
    }

    public static Data newData() {
        return new Data();
    }

    public static <X, Y, Z> HashDataTable<X, Y, Z> newHashDataTable() {
        return new HashDataTable<>();
    }
}
