package javaEEServlets;

import calculation.ExchangeRateCalculation;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "exchangeServlet", value = "/exchange")
public class ExchangeServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String amount = request.getParameter("amount");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Используем Gson для сериализации объекта в JSON
        Gson gson = new Gson();
        String json = gson.toJson(ExchangeRateCalculation.calculate(from, to, Double.parseDouble(amount)));
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
