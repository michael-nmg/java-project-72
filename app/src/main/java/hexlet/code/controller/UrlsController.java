package hexlet.code.controller;

import java.net.URI;
import java.util.List;
import java.sql.Timestamp;
import java.util.Collections;
import java.time.LocalDateTime;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.sql.SQLException;
import java.net.URISyntaxException;
import java.net.MalformedURLException;

import hexlet.code.util.Util;
import hexlet.code.util.RoutNames;
import hexlet.code.model.Url;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.repository.UrlRepository;


public class UrlsController {
    public static void index(Context context) throws SQLException {
        List<Url> urls = UrlRepository.getAll();
        var flash = (String) context.consumeSessionAttribute("flash");
        var alertStatus = (String) context.consumeSessionAttribute("alert-status");
        var page = new UrlsPage(urls);
        page.setFlash(flash);
        page.setAlertStatus(alertStatus);

        context.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void show(Context context) throws SQLException {
        var id = context.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id).orElseThrow();
        var page = new UrlPage(url);
        context.render("urls/show.jte", Collections.singletonMap("page", page));
    }

    public static void save(Context context) {
        try {
            var raw = context.formParamAsClass("url", String.class)
                    .check(value -> !value.isEmpty(), "Invalid url")
                    .get().trim();

            var uri = new URI(raw);
            var name = Util.urlToString(uri.toURL());

            if (UrlRepository.findByName(name).isPresent()) {
                throw new SQLException("Url already exists");
            }

            var url = new Url(name, Timestamp.valueOf(LocalDateTime.now()));
            UrlRepository.save(url);

            context.sessionAttribute("flash", "Страница успешно добавлена");
            context.sessionAttribute("alert-status", "success");
        } catch (ValidationException
                 | URISyntaxException
                 | IllegalArgumentException
                 | MalformedURLException exception) {
            context.sessionAttribute("flash", "Некорректный URL");
            context.sessionAttribute("alert-status", "danger");
        } catch (SQLException exception) {
            context.sessionAttribute("flash", "Страница уже существует");
            context.sessionAttribute("alert-status", "warning");
        } finally {
            context.redirect(RoutNames.urlsPath());
        }
    }
}
