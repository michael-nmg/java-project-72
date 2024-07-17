package hexlet.code.repository;

import hexlet.code.model.Url;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;

public class UrlRepository extends BaseRepository {

    public static Long save(Url url) throws SQLException {
        var query = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            var timestamp = Timestamp.valueOf(url.getCreatedAt());
            state.setString(1, url.getName());
            state.setTimestamp(2, timestamp);
            state.executeUpdate();
            var keys = state.getGeneratedKeys();

            if (keys.next()) {
                var id = keys.getLong("id");
                url.setId(id);
                return id;
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        var query = "SELECT id, name, created_at FROM urls WHERE name = ?";

        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(query)) {
            state.setString(1, name);
            var result = state.executeQuery();
            if (result.next()) {
                var id = result.getLong("id");
                var createdAt = result.getTimestamp("created_at").toLocalDateTime();
                var url = new Url(id, name, createdAt);
                return Optional.of(url);
            }
        }
        return Optional.empty();
    }

    public static List<Url> getAll() throws SQLException {
        var query = "SELECT id, name, created_at FROM urls";

        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(query)) {
            var result = state.executeQuery();
            List<Url> urls = new ArrayList<>();

            while (result.next()) {
                var id = result.getLong("id");
                var name = result.getString("name");
                var createdAt = result.getTimestamp("created_at").toLocalDateTime();
                var url = new Url(id, name, createdAt);
                urls.add(url);
            }

            return urls;
        }
    }

}
