package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Url {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public Url(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        var url = (Url) obj;
        return url.getName().equals(name) && url.getId().equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
