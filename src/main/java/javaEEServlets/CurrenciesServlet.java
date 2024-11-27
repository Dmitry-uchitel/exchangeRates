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

@WebServlet(name = "currenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Используем Gson для сериализации объекта в JSON
        Gson gson = new Gson();
        String json;
        try {
            json = gson.toJson(CurrenciesDatabase.getAllCurrencies());
        } catch (SQLException e) {
            json = e.getMessage();
        }

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fullName = request.getParameter("name");
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String json;
        try {
            json = gson.toJson(CurrenciesDatabase.insertCurrency(code.toUpperCase(), fullName, sign));
        } catch (SQLException e) {
            json = e.getMessage();
        }
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();

    }
}
