package hexlet.code.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import hexlet.code.App;
import hexlet.code.model.Url;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.sql.Timestamp;
import java.io.IOException;
import java.sql.SQLException;

import static hexlet.code.util.Util.readResourceFile;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UrlRepositoryTest {

    public static final String SQL_FILE = "test.sql";
    private static final String DB_H2 = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1;";
    private static final Url URL1 = new Url(1L, "name1", Timestamp.valueOf("2007-12-03 10:15:30"));
    private static final Url URL2 = new Url(2L, "name2", Timestamp.valueOf("2007-12-03 10:15:35"));

    @BeforeAll
    static void setUp() throws IOException, SQLException {
        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl(DB_H2);
        HikariDataSource dataSource = new HikariDataSource(hikari);
        BaseRepository.dataSource = dataSource;

        var createTable = readResourceFile(SQL_FILE, App.class);
        var query = "INSERT INTO urls (name, created_at) VALUES ('name1', '2007-12-03 10:15:30');";

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(createTable);
            statement.executeUpdate(query);
        }
    }

    @Test
    @Order(1)
    void save() throws SQLException {
        var expected = URL2.getId();
        var actual = UrlRepository.save(URL2);
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void findById() throws SQLException {
        var id = URL2.getId();
        var actual = UrlRepository.find(id).orElse(null);
        assertNotNull(actual);
        assertEquals(URL2, actual);
    }

    @Test
    @Order(3)
    void findByName() throws SQLException {
        var name = URL1.getName();
        var actual = UrlRepository.find(name).orElse(null);
        assertNotNull(actual);
        assertEquals(URL1, actual);
    }

    @Test
    @Order(4)
    void findByNameEmpty() throws SQLException {
        var name = "name";
        var actual = UrlRepository.find(name).isEmpty();
        assertTrue(actual);
    }

    @Test
    @Order(5)
    void getAll() throws SQLException {
        List<Url> expected = List.of(URL1, URL2);
        List<Url> actual = UrlRepository.getAll();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

}
