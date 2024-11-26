package javaEEServlets;

import calculation.ExchangeRateCalculation;
import com.google.gson.Gson;
import database.CurrenciesDatabase;
import database.ExchangeRatesDatabase;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "exchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String codeBase = request.getRequestURI().substring(14,17);
        String codeTarget = request.getRequestURI().substring(17);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Используем Gson для сериализации объекта в JSON
        Gson gson = new Gson();
        String json = gson.toJson(ExchangeRatesDatabase.getExchangeRateByCode(codeBase, codeTarget));
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codeBase = request.getRequestURI().substring(14,17);
        String codeTarget = request.getRequestURI().substring(17);
        String rate = request.getParameter("rate");
        ExchangeRatesDatabase.updateExchangeRate(codeBase, codeTarget, Double.parseDouble(rate));
    }
}
