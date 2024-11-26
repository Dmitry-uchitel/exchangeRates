package javaEEServlets;

import com.google.gson.Gson;
import database.ExchangeRatesDatabase;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Используем Gson для сериализации объекта в JSON
        Gson gson = new Gson();
        String json = gson.toJson(ExchangeRatesDatabase.getAllExchangeRates());
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String baseCurrencyCode = request.getParameter("baseCurrencyCode");
        String targetCurrencyCode = request.getParameter("targetCurrencyCode");
        String rate = request.getParameter("rate");
        ExchangeRatesDatabase.insertExchangeRate(baseCurrencyCode, targetCurrencyCode, Double.parseDouble(rate));
    }
}
