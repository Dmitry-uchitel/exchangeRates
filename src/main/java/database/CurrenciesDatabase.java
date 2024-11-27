package database;

import essence.Currency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesDatabase {
    private static final String URL = "jdbc:sqlite:C:/SQLLite/database1/database1.db";

    public static Currency insertCurrency(String code, String fullname, String sign) throws SQLException {
        String sql = String.format("INSERT INTO Currencies (code, fullname, sign) VALUES ('%s', '%s', '%s');", code, fullname, sign);

        System.out.println(sql);
        Connection connection = DriverManager.getConnection(URL);
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        Currency currency = getCurrencyByCode(code);
        connection.close();
        return currency;
    }


    public static List<Currency> getAllCurrencies() throws SQLException {
        String sql = String.format("SELECT * FROM Currencies;");
        List<Currency> currencyList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            currencyList.add(getCurrencyByQuery(rs));
        }
        connection.close();
        return currencyList;
    }


    public static Currency getCurrencyByCode(String code) throws SQLException {
        String sql = String.format("SELECT * FROM Currencies WHERE code='%s';", code);
        Currency currency = null;
        Connection connection = DriverManager.getConnection(URL);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        currency = getCurrencyByQuery(rs);
        connection.close();
        return currency;
    }

    private static Currency getCurrencyByQuery(ResultSet rs) throws SQLException {
        return new Currency(rs.getInt("id"), rs.getString("code"),
                rs.getString("fullname"), rs.getString("sign"));
    }

}