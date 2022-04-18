package dev.begell.api;
import com.google.gson.Gson;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.start(7000);
    }
}
