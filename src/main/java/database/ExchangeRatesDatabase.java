package database;

import essence.Currency;
import essence.ExchangeRate;
import essence.ExchangeRateWithId;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDatabase {
    private static final String URL = "jdbc:sqlite:C:/SQLLite/database1/database1.db";

    public static ExchangeRate insertExchangeRate(String baseCurrencyCode, String targetCurrencyCode, Double rate) {
        String sqlId1 = "Select * from Currencies where code='"+baseCurrencyCode+"';";
        String sqlId2 = "Select * from Currencies where code='"+targetCurrencyCode+"';";
        ExchangeRate exchangeRate = null;

        try (Connection connection = DriverManager.getConnection(URL)) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement()) {
                    ResultSet rs = stmt.executeQuery(sqlId1);
                    Currency currencyBase = CurrenciesDatabase.getCurrencyByQuery(rs);
                    rs = stmt.executeQuery(sqlId2);
                    Currency currencyTarget = CurrenciesDatabase.getCurrencyByQuery(rs);

                    String sqlInsert = "insert into ExchangeRates (BaseCurrencyId, targetCurrencyId, Rate) VALUES ('"
                            + currencyBase.getId() + "', '" + currencyTarget.getId() + "', " + rate + ")";
                    stmt.execute(sqlInsert);

                    String sqlGetId = "Select id from ExchangeRates where BaseCurrencyId="
                            + currencyBase.getId() + " and TargetCurrencyId = "+ currencyTarget.getId() + ";";
                    rs = stmt.executeQuery(sqlGetId);
                    Integer id = rs.getInt("id");
                    exchangeRate = new ExchangeRate(id, currencyBase, currencyTarget, rate);
                    System.out.println("Data inserted");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exchangeRate;
    }

    public static ExchangeRate updateExchangeRate(String baseCurrencyCode, String targetCurrencyCode, Double rate) {
        String sqlId1 = "Select * from Currencies where code='"+baseCurrencyCode+"';";
        String sqlId2 = "Select * from Currencies where code='"+targetCurrencyCode+"';";
        ExchangeRate exchangeRate = null;

        try (Connection connection = DriverManager.getConnection(URL)) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement()) {
                    ResultSet rs = stmt.executeQuery(sqlId1);
                    Currency currencyBase = CurrenciesDatabase.getCurrencyByQuery(rs);
                    rs = stmt.executeQuery(sqlId2);
                    Currency currencyTarget = CurrenciesDatabase.getCurrencyByQuery(rs);

                    String sqlGetId = "Select id from ExchangeRates where BaseCurrencyId="
                            + currencyBase.getId() + " and TargetCurrencyId = "+ currencyTarget.getId() + ";";
                    rs = stmt.executeQuery(sqlGetId);
                    Integer id = rs.getInt("id");

                    String sqlInsert = "update ExchangeRates set Rate =" + rate + " where id = "+ id + ";";
                    stmt.executeUpdate(sqlInsert);

                    exchangeRate = new ExchangeRate(id, currencyBase, currencyTarget, rate);
                    System.out.println("Data inserted");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exchangeRate;
    }

    public static List<ExchangeRateWithId> getAllExchangeRatesWithId() {
        String sql = "SELECT * FROM ExchangeRates;";
        List<ExchangeRateWithId> exchangeRateWithIdList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL)) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        exchangeRateWithIdList.add(getExchangeRateWithIdByQuery(rs));
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exchangeRateWithIdList;
    }

    public static ExchangeRateWithId getExchangeRateWithIdByQuery(ResultSet rs) throws SQLException {
        return new ExchangeRateWithId(rs.getInt("id"), rs.getInt("baseCurrencyId"),
                rs.getInt("targetCurrencyId"), rs.getDouble("rate"));
    }

    public static List<ExchangeRate> getAllExchangeRatesFromExchangeRatesWithId(List<ExchangeRateWithId> exchangeRateWithIdList) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (ExchangeRateWithId exchangeRateWithId:exchangeRateWithIdList){
            exchangeRates.add(ExchangeRateFromExchangeRateWithId(exchangeRateWithId));
        }
        return exchangeRates;
    }

    public static ExchangeRate ExchangeRateFromExchangeRateWithId(ExchangeRateWithId exchangeRateWithId){
        Integer id = exchangeRateWithId.getId();
        Currency currencyBase = CurrenciesDatabase.getCurrencyById(exchangeRateWithId.getCurrencyBaseId());
        Currency currencyTarget = CurrenciesDatabase.getCurrencyById(exchangeRateWithId.getCurrencyTargetId());
        Double rate = exchangeRateWithId.getRate();
        return new ExchangeRate(id, currencyBase, currencyTarget, rate);
    }

    public static ExchangeRate getExchangeRateByCode(String codeBase, String codeTarget){
        Currency currencyBase = CurrenciesDatabase.getCurrency(codeBase);
        Currency currencyTarget = CurrenciesDatabase.getCurrency(codeTarget);
        ExchangeRateWithId exchangeRateWithId= getExchangeRateById(currencyBase.getId(), currencyTarget.getId());
        return ExchangeRateFromExchangeRateWithId(exchangeRateWithId);
    }

    public static ExchangeRateWithId getExchangeRateById(Integer idBase, Integer idTarget){
        String sql = String.format("SELECT * FROM ExchangeRates where (BaseCurrencyId = %s AND TargetCurrencyId = %s OR " +
                "TargetCurrencyId = %s AND BaseCurrencyId = %s)", idBase, idTarget, idBase, idTarget);

        ExchangeRateWithId exchangeRateWithId = null;
        try (Connection connection = DriverManager.getConnection(URL)) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    exchangeRateWithId = getExchangeRateWithIdByQuery(rs);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exchangeRateWithId;

    }

}
