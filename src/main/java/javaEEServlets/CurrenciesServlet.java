package javaEEServlets;

import com.google.gson.Gson;
import database.CurrenciesDatabase;
import essence.Currency;
import java.sql.DriverManager;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "currenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        // Используем Gson для сериализации объекта в JSON
        Gson gson = new Gson();

        String json = gson.toJson(CurrenciesDatabase.getAllCurrencies());
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    public void destroy() {
    }
}
