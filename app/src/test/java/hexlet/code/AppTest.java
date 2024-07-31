package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.util.RoutNames;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import java.sql.Timestamp;
import java.io.IOException;
import java.sql.SQLException;

import static hexlet.code.util.Util.readResourceFile;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
class AppTest {

    private static Javalin app;
    private static String host;
    private static MockWebServer mockServer;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        app = App.getApp();
    }

    @BeforeAll
    static void serverUp() throws IOException {
        mockServer = new MockWebServer();
        var body = readResourceFile("test.html", AppTest.class);
        MockResponse response = new MockResponse()
                .setResponseCode(HttpStatus.OK.getCode())
                .setBody(body);
        mockServer.enqueue(response);
        mockServer.start();
    }

    @AfterAll
    static void serverDown() throws IOException {
        mockServer.shutdown();
        app.stop();
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
            assertTrue(body.string().contains("https://www.example.com"));
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
            var request = "url=https://www.page.com";
            try (var response = client.post(RoutNames.urlsPath(), request);) {
                var body = response.body();
                assertEquals(HttpStatus.OK.getCode(), response.code());
                assertNotNull(body);
                assertTrue(body.string().contains("https://www.page.com"));
            }
        });
    }

    @Test
    void postNewCheck() throws SQLException {
        host = mockServer.url("/").toString();
        var id = 1L;
        var request = "id=" + id;
        var url = new Url(host, Timestamp.valueOf("1999-12-31 23:59:59"));
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            try (var response = client.post(RoutNames.urlCheckPath(id), request)) {
                var urlChecks = UrlCheckRepository.getAllByUrl(id);
                assertEquals(1, urlChecks.size());
                var urlCheck = urlChecks.get(0);

                var expectedStatus = 200;
                var expectedH1 = "H1 tag";
                var expectedTitle = "Title";
                var expectedDescription = "description content";

                assertEquals(expectedH1, urlCheck.getH1());
                assertEquals(expectedTitle, urlCheck.getTitle());
                assertEquals(expectedStatus, urlCheck.getStatusCode());
                assertEquals(expectedDescription, urlCheck.getDescription());
            }
        });
    }

}
