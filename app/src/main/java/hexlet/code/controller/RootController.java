package hexlet.code.controller;

import hexlet.code.dto.BasePage;

import io.javalin.http.Context;

import java.util.Collections;

public class RootController {
    public static void index(Context context) {
        var flash = (String) context.consumeSessionAttribute("flash");
        var alertStatus = (String) context.consumeSessionAttribute("alert-status");
        var page = new BasePage(flash, alertStatus);
        context.render("index.jte", Collections.singletonMap("page", page));
    }
}
