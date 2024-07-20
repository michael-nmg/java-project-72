package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import hexlet.code.dto.BasePage;

import lombok.Getter;
import lombok.AllArgsConstructor;


@Getter
@AllArgsConstructor
public final class UrlPage extends BasePage {
    private Url url;
}
