package hexlet.code.repository;

import hexlet.code.App;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.util.List;
import java.sql.Timestamp;
import java.io.IOException;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import static hexlet.code.util.Util.readResourceFile;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UrlCheckRepositoryTest {

    public static final String SQL_FILE = "test.sql";
    private static final String DB_H2 = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1;";

    private static final String INSERT_URL = """
            INSERT INTO urls (name, created_at)
            VALUES ('name1', '1999-12-31 23:58:59')""";

    private static final String INSERT_CHECK = """
            INSERT INTO url_checks (title, h1, url_id, status_code, created_at, description)
            VALUES ('title', 'h1', 1, 200, '1999-12-31 23:58:59', 'description')""";

    private static final Timestamp CREATED_AT_1 = Timestamp.valueOf("1999-12-31 23:58:59");
    private static final Timestamp CREATED_AT_2 = Timestamp.valueOf("1999-12-31 23:59:59");
    private static final Url URL = new Url(1L, "name", CREATED_AT_1);
    private static final UrlCheck URL_CHECK = new UrlCheck(
            1L,
            1L,
            200,
            "title",
            "h1",
            "description",
            CREATED_AT_1, URL);

    private static final UrlCheck URL_CHECK_2 = new UrlCheck(
            2L,
            1L,
            200,
            "title2",
            "h1",
            "description2",
            CREATED_AT_2, URL);

    @BeforeAll
    static void setUp() throws IOException, SQLException {
        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl(DB_H2);
        HikariDataSource dataSource = new HikariDataSource(hikari);
        BaseRepository.dataSource = dataSource;

        var createTable = readResourceFile(SQL_FILE, App.class);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(createTable);
            statement.executeUpdate(INSERT_URL);
            statement.executeUpdate(INSERT_CHECK);
        }
    }

    @Test
    @Order(1)
    void save() throws SQLException {
        var expected = URL_CHECK_2.getId();
        var actual = UrlCheckRepository.save(URL_CHECK_2);
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void find() throws SQLException {
        var id = URL_CHECK.getId();
        var actual = UrlCheckRepository.find(id).orElse(null);
        assertNotNull(actual);
        actual.setCheckedUrl(URL);
        assertEquals(URL_CHECK, actual);
    }

    @Test
    @Order(3)
    void findUnknown() throws SQLException {
        var id = 3L;
        var actual = UrlCheckRepository.find(id).isEmpty();
        assertTrue(actual);
    }

    @Test
    @Order(4)
    void getAllByUrl() throws SQLException {
        List<UrlCheck> expected = List.of(URL_CHECK, URL_CHECK_2);
        List<UrlCheck> actual = UrlCheckRepository.getAllByUrl(1L);
        actual.forEach(check -> check.setCheckedUrl(URL));
        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @Order(5)
    void getAllLast() throws SQLException {
        var actual = UrlCheckRepository.getAllLast();
        var actualCheck = actual.get(URL.getId());
        actualCheck.setCheckedUrl(URL);
        assertEquals(1, actual.size());
        assertEquals(URL_CHECK_2, actualCheck);
    }

}
