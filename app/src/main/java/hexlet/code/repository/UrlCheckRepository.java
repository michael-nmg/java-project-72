package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


public class UrlCheckRepository extends BaseRepository {

    private static final String GET_BY_ID = "SELECT url_checks.* FROM url_checks WHERE id = ?";

    private static final String GET_ALL = """
            SELECT url_checks.*
            FROM url_checks
            WHERE url_id = ?
            ORDER BY url_checks.id ASC""";

    private static final String INSERT = """
            INSERT INTO url_checks (url_id, status_code, h1, title, created_at, description)
            VALUES (?, ?, ?, ?, ?, ?)""";

    private static final String GET_ALL_LAST = """
            SELECT DISTINCT ON (url_id) url_checks.*
            FROM url_checks
            ORDER BY url_id ASC, id DESC;""";

    public static Long save(UrlCheck check) throws SQLException {
        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            state.setLong(1, check.getUrlId());
            state.setInt(2, check.getStatusCode());
            state.setString(3, check.getH1());
            state.setString(4, check.getTitle());
            state.setTimestamp(5, check.getCreatedAt());
            state.setString(6, check.getDescription());
            state.executeUpdate();

            var keys = state.getGeneratedKeys();
            if (keys.next()) {
                var id = keys.getLong(1);
                check.setId(id);
                return id;
            } else {
                throw new SQLException("Failed to insert new url check");
            }
        }
    }

    public static Optional<UrlCheck> find(Long id) throws SQLException {
        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(GET_BY_ID)) {
            state.setLong(1, id);
            var result = state.executeQuery();

            if (result.next()) {
                var urlId = result.getLong("url_id");
                var urlCheck = fabricMethod(result, id, urlId);
                return Optional.of(urlCheck);
            }
        }

        return Optional.empty();
    }

    public static List<UrlCheck> getAllByUrl(Long urlId) throws SQLException {
        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(GET_ALL)) {
            state.setLong(1, urlId);
            var result = state.executeQuery();
            List<UrlCheck> urlChecks = new ArrayList<>();

            while (result.next()) {
                var id = result.getLong("id");
                var urlCheck = fabricMethod(result, id, urlId);
                urlChecks.add(urlCheck);
            }

            return urlChecks;
        }
    }

    public static Map<Long, UrlCheck> getAllLast() throws SQLException {
        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(GET_ALL_LAST)) {
            var result = state.executeQuery();
            Map<Long, UrlCheck> urlsLastCheck = new HashMap<>();

            while (result.next()) {
                var id = result.getLong("id");
                var urlId = result.getLong("url_id");
                var urlCheck = fabricMethod(result, id, urlId);
                urlsLastCheck.put(urlId, urlCheck);
            }
            return urlsLastCheck;
        }
    }

    private static UrlCheck fabricMethod(ResultSet result, Long id, Long urlId) throws SQLException {
        var statusCode = result.getInt("status_code");
        var h1 = result.getString("h1");
        var title = result.getString("title");
        var createdAt = result.getTimestamp("created_at");
        var description = result.getString("description");
        var urlCheck = new UrlCheck(urlId, statusCode, title, h1, createdAt);
        urlCheck.setId(id);
        urlCheck.setDescription(description);
        return urlCheck;
    }

}
