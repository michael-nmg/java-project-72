package hexlet.code.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UrlTest {

    private static Url url;

    @BeforeEach
    public void init() {
        url = new Url(1L, "name", LocalDateTime.MIN);
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
        assertEquals(LocalDateTime.MIN, url.getCreatedAt());
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
        var now = LocalDateTime.now();
        url.setCreatedAt(now);
        assertEquals(now, url.getCreatedAt());
    }

    @Test
    void equalsBySelf() {
        assertEquals(url, url);
    }

    @Test
    void notEquals() {
        var actual = new Url(2L, "name2", LocalDateTime.now());
        assertNotEquals(url, actual);
    }

    @Test
    void hashCodeEquals() {
        var actual = new Url(1L, "name", LocalDateTime.MIN).hashCode();
        var expected = url.hashCode();
        assertEquals(expected, actual);
    }
}
