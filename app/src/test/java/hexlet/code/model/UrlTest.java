package hexlet.code.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UrlTest {

    private static Url url;

    @BeforeEach
    public void init() {
        url = new Url(1L, "name", Timestamp.valueOf(LocalDateTime.MIN));
    }

    @Test
    void getId() {
        assertEquals(1L, url.getId());
    }

    @Test
    void getName() {
        assertEquals("name", url.getName());
    }

    @Test
    void getCreatedAt() {
        var expected = Timestamp.valueOf(LocalDateTime.MIN);
        var actual = url.getCreatedAt();
        assertEquals(expected, actual);
    }

    @Test
    void setId() {
        url.setId(2L);
        assertEquals(2L, url.getId());
    }

    @Test
    void setName() {
        url.setName("new_name");
        assertEquals("new_name", url.getName());
    }

    @Test
    void setCreatedAt() {
        var now = Timestamp.valueOf(LocalDateTime.now());
        url.setCreatedAt(now);
        assertEquals(now, url.getCreatedAt());
    }

    @Test
    void equalsBySelf() {
        assertEquals(url, url);
    }

    @Test
    void notEquals() {
        var now = Timestamp.valueOf(LocalDateTime.now());
        var actual = new Url(2L, "name2", now);
        assertNotEquals(url, actual);
    }

    @Test
    void hashCodeEquals() {
        var createdAt = Timestamp.valueOf(LocalDateTime.MIN);
        var actual = new Url(1L, "name", createdAt).hashCode();
        var expected = url.hashCode();
        assertEquals(expected, actual);
    }

}
