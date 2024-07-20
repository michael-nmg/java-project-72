package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import hexlet.code.dto.BasePage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public final class UrlsPage extends BasePage {
    private final List<Url> urls;
}
