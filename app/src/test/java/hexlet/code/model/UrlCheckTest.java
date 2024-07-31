package hexlet.code.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UrlCheckTest {

    private Url url;
    private UrlCheck urlCheck;
    private Timestamp timestamp;

    @BeforeEach
    void setUp() {
        timestamp = Timestamp.valueOf("1999-12-31 23:58:59");
        url = new Url(1L, "name", Timestamp.valueOf("1999-12-31 23:58:59"));
        urlCheck = new UrlCheck(1L, 1L, 200, "title", "h1", "description", timestamp, url);
    }

    @Test
    void getId() {
        assertEquals(1L, urlCheck.getId());
    }

    @Test
    void getUrlId() {
        assertEquals(1L, urlCheck.getUrlId());
    }

    @Test
    void getStatusCode() {
        assertEquals(200, urlCheck.getStatusCode());
    }

    @Test
    void getTitle() {
        assertEquals("title", urlCheck.getTitle());
    }

    @Test
    void getH1() {
        assertEquals("h1", urlCheck.getH1());
    }

    @Test
    void getDescription() {
        assertEquals("description", urlCheck.getDescription());
    }

    @Test
    void getUrl() {
        assertEquals(url, urlCheck.getCheckedUrl());
    }

    @Test
    void getCreatedAt() {
        assertEquals(timestamp, urlCheck.getCreatedAt());
    }


    @Test
    void setId() {
        var expected = 2L;
        urlCheck.setId(expected);
        assertEquals(expected, urlCheck.getId());
    }

    @Test
    void setUrlId() {
        var expected = 2L;
        urlCheck.setUrlId(expected);
        assertEquals(expected, urlCheck.getUrlId());
    }

    @Test
    void setStatusCode() {
        var expected = 201;
        urlCheck.setStatusCode(expected);
        assertEquals(expected, urlCheck.getStatusCode());
    }

    @Test
    void setTitle() {
        var expected = "new_title";
        urlCheck.setTitle(expected);
        assertEquals(expected, urlCheck.getTitle());
    }

    @Test
    void setH1() {
        var expected = "h2";
        urlCheck.setH1(expected);
        assertEquals(expected, urlCheck.getH1());
    }

    @Test
    void setDescription() {
        var expected = "new_description";
        urlCheck.setDescription(expected);
        assertEquals(expected, urlCheck.getDescription());
    }

    @Test
    void setUrl() {
        var expected = new Url(2L, "name_2", Timestamp.valueOf(LocalDateTime.now()));
        urlCheck.setCheckedUrl(expected);
        assertEquals(expected, urlCheck.getCheckedUrl());
    }

    @Test
    void setCreatedAt() {
        var expected = Timestamp.valueOf(LocalDateTime.now());
        urlCheck.setCreatedAt(expected);
        assertEquals(expected, urlCheck.getCreatedAt());
    }

    @Test
    void equalsBySelf() {
        assertEquals(urlCheck, urlCheck);
    }

    @Test
    void notEquals() {
        var now = Timestamp.valueOf(LocalDateTime.now());
        var actual = new UrlCheck(2L, 1L, 200, "title2", "h1", "description2", now, url);
        assertNotEquals(url, actual);
    }

    @Test
    void hashCodeEquals() {
        var actual = new UrlCheck(1L, 1L, 200, "title", "h1", "description", timestamp, url).hashCode();
        var expected = urlCheck.hashCode();
        assertEquals(expected, actual);
    }

}
