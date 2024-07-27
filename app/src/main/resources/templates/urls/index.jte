@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.RoutNames
@import static hexlet.code.util.Util.timestampToString
@param UrlsPage page


@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h3>Links</h3>
        <div class="table-responsive">
            <table class="table table-striped table-sm">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Last check</th>
                    <th scope="col">Code</th>
                </tr>
                </thead>
                <tbody>
                @for (var url : page.getUrls())
                    <tr>
                        <td>${url.getId()}</td>
                        <td>
                            <a href="${RoutNames.urlPath(url.getId())}">${url.getName()}</a>
                        </td>
                        <td>${timestampToString(url.getCreatedAt())}</td>
                        <td>200</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>
    </div>
`
)
