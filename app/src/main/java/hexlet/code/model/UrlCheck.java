package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UrlCheck {
    private Long id;
    private Long urlId;
    private Integer statusCode;
    private String title;
    private String h1;
    private String description;
    private Timestamp createdAt;
    private Url checkedUrl;

    public UrlCheck(Long urlId, Integer statusCode, String title, Timestamp createdAt) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.title = title;
        this.createdAt = createdAt;
    }

    public UrlCheck(Long urlId, Integer statusCode, String title, String h1, Timestamp createdAt) {
        this(urlId, statusCode, title, createdAt);
        this.h1 = h1;
    }
}
