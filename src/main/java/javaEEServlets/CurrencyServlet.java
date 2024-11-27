package javaEEServlets;

import com.google.gson.Gson;
import database.CurrenciesDatabase;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "currencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getRequestURI().substring(10);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Используем Gson для сериализации объекта в JSON
        Gson gson = new Gson();
        String json;
        try {
            json = gson.toJson(CurrenciesDatabase.getCurrencyByCode(code.toUpperCase()));
        } catch (SQLException e) {
            json = e.getMessage();
        }
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
