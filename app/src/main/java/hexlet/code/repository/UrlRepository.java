package hexlet.code.repository;

import hexlet.code.model.Url;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import java.sql.Statement;
import java.sql.SQLException;

public class UrlRepository extends BaseRepository {

    private static final String TIME_COLUMN = "created_at";
    private static final String GET_ALL = "SELECT id, name, created_at FROM urls";
    private static final String INSERT = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
    private static final String GET_BY_ID = "SELECT id, name, created_at FROM urls WHERE id = ?";
    private static final String GET_BY_NAME = "SELECT id, name, created_at FROM urls WHERE name = ?";

    public static Long save(Url url) throws SQLException {
        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            var timestamp = url.getCreatedAt();
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

    public static Optional<Url> find(String name) throws SQLException {
        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(GET_BY_NAME)) {
            state.setString(1, name);
            var result = state.executeQuery();
            if (result.next()) {
                var id = result.getLong("id");
                var createdAt = result.getTimestamp(TIME_COLUMN);
                var url = new Url(id, name, createdAt);
                return Optional.of(url);
            }
        }
        return Optional.empty();
    }

    public static Optional<Url> find(Long id) throws SQLException {
        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(GET_BY_ID)) {
            state.setLong(1, id);
            var result = state.executeQuery();
            if (result.next()) {
                var name = result.getString("name");
                var createdAt = result.getTimestamp(TIME_COLUMN);
                var url = new Url(id, name, createdAt);
                return Optional.of(url);
            }
        }
        return Optional.empty();
    }

    public static List<Url> getAll() throws SQLException {
        try (var connection = dataSource.getConnection();
             var state = connection.prepareStatement(GET_ALL)) {
            var result = state.executeQuery();
            List<Url> urls = new ArrayList<>();

            while (result.next()) {
                var id = result.getLong("id");
                var name = result.getString("name");
                var createdAt = result.getTimestamp(TIME_COLUMN);
                var url = new Url(id, name, createdAt);
                urls.add(url);
            }

            return urls;
        }
    }

}
