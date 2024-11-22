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


    public static void insertCurrency(String code, String fullname, String sign) {
        String sql = "INSERT INTO Currencies (code, fullname, sign) VALUES ('"
                + code + "', '" + fullname + "', '" + sign + "')";
        try (Connection connection = DriverManager.getConnection(URL)) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute(sql);
                    System.out.println("Data inserted");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Currency> getAllCurrencies() {
        String sql = "SELECT * FROM Currencies;";
        List<Currency> currencyList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL)) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        currencyList.add(getCurrencyByQuery(rs));

                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return currencyList;
    }

    public static Currency getCurrency(String code) {
        String sql = "SELECT * FROM Currencies WHERE code='" + code + "';";

        Currency currency = null;
        try (Connection connection = DriverManager.getConnection(URL)) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    currency = getCurrencyByQuery(rs);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return currency;
    }


    public static Currency getCurrencyById(Integer id) {
        String sql = "SELECT * FROM Currencies WHERE id='" + id + "';";
        Currency currency = null;
        try (Connection connection = DriverManager.getConnection(URL)) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    currency = getCurrencyByQuery(rs);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return currency;
    }

    public static Currency getCurrencyByQuery(ResultSet rs) throws SQLException {
        return new Currency(rs.getInt("id"), rs.getString("code"),
                rs.getString("fullname"), rs.getString("sign"));
    }

}