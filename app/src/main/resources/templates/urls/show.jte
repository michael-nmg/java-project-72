@import hexlet.code.dto.urls.UrlPage
@import static hexlet.code.util.Util.timestampToString
@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h3>Url: ${page.getUrl().getName()}</h3>

        <table class="table table-bordered table-hover mt-3">
            <tbody>
            <tr>
                <td>ID</td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Name</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Created at</td>
                <td>${timestampToString(page.getUrl().getCreatedAt())}</td>
            </tr>
            </tbody>
        </table>

        <h3 class="mt-5">Checks</h3>
        <form method="POST" action="/urls/2/checks">
            <button type="submit" class="btn btn-primary btn-sm">Check up</button>
        </form>

        <table class="table table-striped table-hover mt-3">
            <thead>
            <th class="col-1">ID</th>
            <th class="col-1">Code</th>
            <th>Title</th>
            <th>H1</th>
            <th>Description</th>
            <th class="col-2">Date of check</th>
            </thead>
            <tbody>
            <%--        @for (var url : page.getUrls())--%>
            <%--            <tr>--%>
            <%--                <td>${url.getId()}</td>--%>
            <%--                <td>--%>
            <%--                    <a href="${RoutNames.urlPath(url.getId())}">${url.getName()}</a>--%>
            <%--                </td>--%>
            <%--                <td>${url.getCreatedAt().toString()}</td>--%>
            <%--                <td>200</td>--%>
            <%--            </tr>--%>
            <%--        @endfor--%>
            </tbody>
        </table>
    </div>
`
)
