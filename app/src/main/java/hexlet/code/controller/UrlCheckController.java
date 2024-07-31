package hexlet.code.controller;

import hexlet.code.util.RoutNames;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import kong.unirest.core.Unirest;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;


@Slf4j
public class UrlCheckController {

    private static final String FLASH = "flash";
    private static final String ALERT = "alert-status";

    public static void save(Context context) throws SQLException {
        var urlId = context.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        try {
            configUnirest();
            var response = Unirest.get(url.getName()).asString();
            Unirest.shutDown();

            var statusCode = response.getStatus();
            var body = response.getBody();
            Document document = Jsoup.parse(body);

            var h1 = document.select("h1").first();
            var title = document.title().isEmpty() ? null : document.title();
            var timestamp = Timestamp.valueOf(LocalDateTime.now());
            var description = document.select("meta[name=description]").first();
            var urlCheck = new UrlCheck(urlId, statusCode, title, timestamp);
            urlCheck.setH1(h1 != null ? h1.text() : null);
            urlCheck.setDescription(description != null ? description.attr("content") : null);
            UrlCheckRepository.save(urlCheck);

            context.sessionAttribute(FLASH, "Страница успешно проверена");
            context.sessionAttribute(ALERT, "success");
            context.redirect(RoutNames.urlPath(urlId));
        } catch (RuntimeException exception) {
            Unirest.shutDown();
            context.sessionAttribute(FLASH, "Некорректный адрес");
            context.sessionAttribute(ALERT, "danger");
            context.redirect(RoutNames.urlPath(urlId));
        }
    }

    private static void configUnirest() {
        Unirest.config()
                .connectTimeout(5000)
                .followRedirects(false)
                .enableCookieManagement(false);
    }

}
