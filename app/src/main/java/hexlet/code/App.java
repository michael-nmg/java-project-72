package hexlet.code;

import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    private static int getPort() {
        var port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static Javalin getApp() {
        var app = Javalin.create(config ->
                config.bundledPlugins.enableDevLogging());

        app.get(RoutNames.root(), context -> context.result("it's work"));
        return app;
    }

    public static void main(String[] args) {
        getApp().start(getPort());
    }
}
