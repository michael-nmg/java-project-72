@import gg.jte.Content
@import hexlet.code.dto.BasePage

@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

</head>
<body>

<div class="container">
<%--    @if (page != null && page.getFlash() != null)--%>
<%--        @if (page.getAlertStatus().equals("danger"))--%>
<%--            <div class="alert alert-danger" role="alert">--%>
<%--                ${page.getFlash()}--%>
<%--            </div>--%>
<%--        @elseif(page.getAlertStatus().equals("success"))--%>
<%--            <div class="alert alert-success" role="alert">--%>
<%--                ${page.getFlash()}--%>
<%--            </div>--%>
<%--        @endif--%>
<%--    @endif--%>
</div>

<div class="container">
    ${content}
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

</body>
</html>