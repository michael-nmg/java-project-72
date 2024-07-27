package hexlet.code.controller;

import hexlet.code.util.RoutNames;

import java.util.List;
import java.util.Collections;
import java.time.LocalDateTime;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.MalformedURLException;

import java.sql.Timestamp;
import java.sql.SQLException;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;

import hexlet.code.util.Util;
import hexlet.code.model.Url;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.repository.UrlRepository;


public class UrlsController {
    private static final String FLASH = "flash";
    private static final String ALERT = "alert-status";

    public static void index(Context context) throws SQLException {
        List<Url> urls = UrlRepository.getAll();
        var flash = (String) context.consumeSessionAttribute(FLASH);
        var alertStatus = (String) context.consumeSessionAttribute(ALERT);
        var page = new UrlsPage(urls);
        page.setFlash(flash);
        page.setAlertStatus(alertStatus);

        context.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void show(Context context) throws SQLException {
        var id = context.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id);

        if (url.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND).html("error/404");
        } else {
            var page = new UrlPage(url.get());
            context.render("urls/show.jte", Collections.singletonMap("page", page));
        }
    }

    public static void save(Context context) throws SQLException {
        try {
            var raw = context.formParamAsClass("url", String.class)
                    .check(value -> !value.isEmpty(), "Empty url")
                    .get().trim();

            var uri = new URI(raw);
            var name = Util.urlToString(uri.toURL());

            if (UrlRepository.find(name).isPresent()) {
                context.sessionAttribute(FLASH, "Страница уже существует");
                context.sessionAttribute(ALERT, "warning");
            } else {
                var url = new Url(name, Timestamp.valueOf(LocalDateTime.now()));
                UrlRepository.save(url);
                context.sessionAttribute(FLASH, "Страница успешно добавлена");
                context.sessionAttribute(ALERT, "success");
            }
            context.redirect(RoutNames.urlsPath());
        } catch (ValidationException
                 | URISyntaxException
                 | IllegalArgumentException
                 | MalformedURLException exception) {
            context.sessionAttribute(FLASH, "Некорректный URL");
            context.sessionAttribute(ALERT, "danger");
            context.redirect(RoutNames.rootPath());
        }
    }

}
