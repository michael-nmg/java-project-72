@template.layout.page(
content = @`
    <div class="container-fluid bg-dark p-5">
        <div class="row">
            <div class="col-md-10 col-lg-8 mx-auto text-white">

                <h4 class="display-3 mb-0">Links analyzer</h4>
                <p class="lead">Check for SEO</p>

                <form action="/urls" method="POST" class="rss-form text-body">
                    <div class="row">
                        <div class="col">
                            <div class="form-floating">
                                <input id="url-input" name="url" type="text" aria-label="url"
                                       class="form-control w-100" placeholder="link" autocomplete="off"
                                       required autofocus>
                                <label for="url-input">Link</label>
                            </div>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="h-100 btn btn-primary px-sm-5">Check</button>
                        </div>
                    </div>
                </form>

                <p class="mt-2 mb-0 text-secondary">Example: https://www.example.com</p>

            </div>
        </div>
    </div>
`
)
