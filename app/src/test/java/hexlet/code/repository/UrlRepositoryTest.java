package hexlet.code.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import hexlet.code.App;
import hexlet.code.model.Url;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UrlRepositoryTest {

    private static Url url1 = new Url("name1", LocalDateTime.now());
    private static Url url2 = new Url("name2", LocalDateTime.now());
    private static final String DB_NAME = "testDB";
    private static final String DB_ENV = "JDBC_DATABASE_URL";
    private static final String DB_H2 = "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;";

    @BeforeEach
    void setUp() throws IOException, SQLException {
        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl(getLinkDB());
        HikariDataSource dataSource = new HikariDataSource(hikari);
        initDataBase(dataSource);
        BaseRepository.dataSource = dataSource;
    }

    @Test
    void save() throws SQLException {
        var expected = 1L;
        var actual = UrlRepository.save(url1);
        assertEquals(expected, actual);
    }

    @Test
    void findByName() throws SQLException {
        UrlRepository.save(url1);
        var name = url1.getName();
        var actual = UrlRepository.findByName(name).orElse(null);
        assertNotNull(actual);
        assertEquals(url1, actual);
    }

    @Test
    void findByNameEmpty() throws SQLException {
        var name = "name";
        var actual = UrlRepository.findByName(name).isEmpty();
        assertTrue(actual);
    }


    @Test
    void getAll() throws SQLException {
        UrlRepository.save(url1);
        UrlRepository.save(url2);
        List<Url> expected = List.of(url1, url2);
        List<Url> actual = UrlRepository.getAll();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private static void initDataBase(HikariDataSource dataSource) throws SQLException, IOException {
        var sql = readResourceFile("schema.sql");

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private static String getLinkDB() {
        var h2Url = String.format(DB_H2, DB_NAME);
        return System.getenv().getOrDefault(DB_ENV, h2Url);
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

}
