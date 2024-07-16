package hexlet.code;

import io.javalin.Javalin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    private static int getPort() {
        var port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    private static String getLinkDB(String name) {
        return String.format("jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;", name);
    }

    public static Javalin getApp() {
        var app = Javalin.create(config ->
                config.bundledPlugins.enableDevLogging());

        app.get(RoutNames.root(), context -> context.result("it's work"));
        return app;
    }

    public static void main(String[] args) {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(getLinkDB("project"));
//        HikariDataSource dataSource = new HikariDataSource(config);


        getApp().start(getPort());
    }
}
