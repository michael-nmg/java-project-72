package hexlet.code.util;

public class RoutNames {
    public static String rootPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(Long id) {
        return "/urls/" + id;
    }

    public static String urlPath(String id) {
        return "/urls/" + id;
    }

    public static String urlCheckPath(Long id) {
        return "/urls/" + id + "/checks";
    }

    public static String urlCheckPath(String id) {
        return "/urls/" + id + "/checks";
    }
}
