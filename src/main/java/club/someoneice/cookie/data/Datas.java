package club.someoneice.cookie.data;

public class Datas {
    public static HashData newHashData() {
        return new HashData();
    }

    public static ConcurrentHashData newConcurrentHashData() {
        return new ConcurrentHashData();
    }

    public static HashData newHashData(String name) {
        return new HashData(name);
    }

    public static ConcurrentHashData newConcurrentHashData(String name) {
        return new ConcurrentHashData(name);
    }
}
