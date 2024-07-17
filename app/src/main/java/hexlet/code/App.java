package hexlet.code;

import hexlet.code.controller.RootController;
import hexlet.code.repository.BaseRepository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.javalin.Javalin;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.rendering.template.JavalinJte;

import java.io.IOException;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static final String DB_NAME = "project";
    public static final String DB_ENV = "JDBC_DATABASE_URL";
    public static final String DB_H2 = "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;";

    public static void main(String[] args) throws SQLException, IOException {
        getApp().start(getPort());
    }

    public static Javalin getApp() throws SQLException, IOException {
        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl(getLinkDB());
        HikariDataSource dataSource = new HikariDataSource(hikari);
        BaseRepository.dataSource = dataSource;

        initDataBase(dataSource);

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(RoutNames.rootPath(), RootController::index);
        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static void initDataBase(HikariDataSource dataSource) throws SQLException, IOException {
        var sql = readResourceFile("schema.sql");

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private static String readResourceFile(String filename) throws IOException {
        var url = App.class.getClassLoader().getResourceAsStream(filename);

        if (url == null) {
            throw new IOException("Undefined schema database");
        }

        try (var buffer = new BufferedReader(new InputStreamReader(url))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    private static int getPort() {
        var port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    private static String getLinkDB() {
        var h2Url = String.format(DB_H2, DB_NAME);
        return System.getenv().getOrDefault(DB_ENV, h2Url);
    }

}
