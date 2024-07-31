@import hexlet.code.util.RoutNames
@import hexlet.code.dto.urls.UrlPage
@import static hexlet.code.util.Util.timestampToString
@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        !{var url = page.getUrl();}
        <h3>Url: ${url.getName()}</h3>

        <table class="table table-bordered table-hover mt-3">
            <tbody>
            <tr>
                <td>ID</td>
                <td>${url.getId()}</td>
            </tr>
            <tr>
                <td>Name</td>
                <td><a href="${url.getName()}">${url.getName()}</a></td>
            </tr>
            <tr>
                <td>Created at</td>
                <td>${timestampToString(url.getCreatedAt())}</td>
            </tr>
            </tbody>
        </table>

        <h3 class="mt-5">Checks</h3>
        <form method="POST" action="${RoutNames.urlCheckPath(url.getId())}">
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
            !{var checks = url.getChecks();}
            @if (checks != null && !checks.isEmpty())
                @for (var check : checks)
                    <tr>
                        <td>${check.getId()}</td>
                        <td>${check.getStatusCode()}</td>
                        <td>${check.getTitle()}</td>
                        <td>${check.getH1()}</td>
                        <td>${check.getDescription()}</td>
                        <td>${timestampToString(check.getCreatedAt())}</td>
                    </tr>
                @endfor
            @else
                <tr>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                </tr>
            @endif
            </tbody>
        </table>
    </div>
`
)
