package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.util.Util;
import hexlet.code.util.RoutNames;
import hexlet.code.model.UrlCheck;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;

import java.util.List;
import java.util.Collections;
import java.time.LocalDateTime;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.MalformedURLException;

import java.sql.Timestamp;
import java.sql.SQLException;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;


public class UrlController {
    private static final String FLASH = "flash";
    private static final String ALERT = "alert-status";

    public static void index(Context context) throws SQLException {
        var flash = (String) context.consumeSessionAttribute(FLASH);
        var alertStatus = (String) context.consumeSessionAttribute(ALERT);

        List<Url> urls = UrlRepository.getAll();
        var urlsLastCheck = UrlCheckRepository.getAllLast();

        var page = new UrlsPage(urls, urlsLastCheck);
        page.setFlash(flash);
        page.setAlertStatus(alertStatus);
        context.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void show(Context context) throws SQLException {
        var id = context.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("error/404"));
        var flash = (String) context.consumeSessionAttribute(FLASH);
        var alertStatus = (String) context.consumeSessionAttribute(ALERT);

        List<UrlCheck> checks = UrlCheckRepository.getAllByUrl(id);
        url.setChecks(checks);
        var page = new UrlPage(url);
        page.setFlash(flash);
        page.setAlertStatus(alertStatus);
        context.render("urls/show.jte", Collections.singletonMap("page", page));
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
