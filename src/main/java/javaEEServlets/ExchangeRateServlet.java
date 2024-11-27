package javaEEServlets;

import com.google.gson.Gson;
import database.ExchangeRatesDatabase;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "exchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String codeBase = request.getRequestURI().substring(14, 17);
        String codeTarget = request.getRequestURI().substring(17);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Используем Gson для сериализации объекта в JSON
        Gson gson = new Gson();
        String json;
        try {
            json = gson.toJson(ExchangeRatesDatabase.getExchangeRateByCode(codeBase, codeTarget));
        } catch (SQLException e) {
            json = e.getMessage();
        }
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codeBase = request.getRequestURI().substring(14, 17);
        String codeTarget = request.getRequestURI().substring(17);
        String rate = request.getParameter("rate");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String json;
        try {
            json = gson.toJson(ExchangeRatesDatabase.updateExchangeRate(codeBase, codeTarget, Double.parseDouble(rate)));
        } catch (SQLException e) {
            json = e.getMessage();
        }
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
