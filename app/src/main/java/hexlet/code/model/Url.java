package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;

import java.util.List;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;
    private List<UrlCheck> checks;

    public Url(String name, Timestamp createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public Url(Long id, String name, Timestamp createdAt) {
        this(name, createdAt);
        this.id = id;
    }
}
