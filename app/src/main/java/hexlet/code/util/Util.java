package hexlet.code.util;

import java.net.URL;
import java.sql.Timestamp;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;


public final class Util {

    private Util() {
    }

    public static String getLinkDB(String env, String secondary) {
        return System.getenv().getOrDefault(env, secondary);
    }

    public static int getPort() {
        var port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static String getSqlFile(String url, String pstgrs, String h2) {
        return url.contains("postgresql") ? pstgrs : h2;
    }

    public static <T> String readResourceFile(String filename, Class<T> clazz) throws IOException {
        var url = clazz.getClassLoader().getResourceAsStream(filename);

        if (url == null) {
            throw new IOException("Undefined schema database");
        }

        try (var buffer = new BufferedReader(new InputStreamReader(url))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    public static String timestampToString(Timestamp time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(time.toLocalDateTime());
    }

    public static String urlToString(URL url) {
        return String.format(
                "%s://%s%s",
                url.getProtocol(),
                url.getHost(),
                url.getPort() > 0 ? ":" + url.getPort() : ""
        );
    }

}
