package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import hexlet.code.dto.BasePage;
import hexlet.code.model.UrlCheck;

import lombok.Getter;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.List;


@Getter
@AllArgsConstructor
public final class UrlsPage extends BasePage {
    private final List<Url> urls;
    private final Map<Long, UrlCheck> urlsLastCheck;
}
