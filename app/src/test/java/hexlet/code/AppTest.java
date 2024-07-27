package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.util.RoutNames;
import hexlet.code.repository.UrlRepository;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Timestamp;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
class AppTest {

    private Javalin app;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        app = App.getApp();
    }

    @Test
    void getRootPath() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(RoutNames.rootPath());
            var body = response.body();
            assertEquals(response.code(), HttpStatus.OK.getCode());
            assertNotNull(body);
            assertTrue(body.string().contains("Check for SEO"));
        });
    }

    @Test
    void getAllUrls() throws SQLException {
        var url = new Url("https://www.example.com", Timestamp.valueOf("1999-12-31 23:59:59"));
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get(RoutNames.urlsPath());
            var body = response.body();
            assertEquals(response.code(), HttpStatus.OK.getCode());
            assertNotNull(body);
            assertTrue(body.string().contains("<a href=\"/urls/1\">https://www.example.com</a>"));
        });
    }

    @Test
    void getUrlById() throws SQLException {
        var url = new Url("https://www.example.com", Timestamp.valueOf("1999-12-31 23:59:59"));
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get(RoutNames.urlPath(1L));
            var body = response.body();
            assertEquals(response.code(), HttpStatus.OK.getCode());
            assertNotNull(body);
            assertTrue(body.string().contains("<td>https://www.example.com</td>"));
        });
    }

    @Test
    void getUrlByUnknownId() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(RoutNames.urlPath(1L));
            var body = response.body();
            assertEquals(response.code(), HttpStatus.NOT_FOUND.getCode());
            assertNotNull(body);
            assertTrue(body.string().contains("error/404"));
        });
    }

    @Test
    void postNewUrl() {
        JavalinTest.test(app, (server, client) -> {
            var request = ("url=https://www.page.com");
            var response = client.post(RoutNames.urlsPath(), request);
            var body = response.body();
            assertEquals(HttpStatus.OK.getCode(), response.code());
            assertNotNull(body);
            assertTrue(body.string().contains("https://www.page.com"));
        });
    }

}
