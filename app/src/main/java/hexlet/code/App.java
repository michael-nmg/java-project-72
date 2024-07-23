package hexlet.code;

import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlsController;
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

import lombok.extern.slf4j.Slf4j;

import hexlet.code.util.RoutNames;

import static hexlet.code.util.Util.getPort;
import static hexlet.code.util.Util.getLinkDB;
import static hexlet.code.util.Util.getSqlFile;
import static hexlet.code.util.Util.readResourceFile;


@Slf4j
public class App {

    public static final String SQL_FILE = "schema.sql";
    public static final String SQL_FILE_H2 = "schema_for_H2.sql";
    public static final String DB_ENV = "JDBC_DATABASE_URL";
    public static final String DB_H2 = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;";

    public static void main(String[] args) throws SQLException, IOException {
        getApp().start(getPort());
    }

    public static Javalin getApp() throws SQLException, IOException {
        var urlDb = getLinkDB(DB_ENV, DB_H2);
        var filename = getSqlFile(urlDb, SQL_FILE, SQL_FILE_H2);
        var createTable = readResourceFile(filename, App.class);

        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl(urlDb);

        HikariDataSource dataSource = new HikariDataSource(hikari);
        BaseRepository.dataSource = dataSource;

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(createTable);
        }

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(RoutNames.rootPath(), RootController::index);
        app.get(RoutNames.urlsPath(), UrlsController::index);
        app.get(RoutNames.urlPath("{id}"), UrlsController::show);

        app.post(RoutNames.urlsPath(), UrlsController::save);
        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

}
