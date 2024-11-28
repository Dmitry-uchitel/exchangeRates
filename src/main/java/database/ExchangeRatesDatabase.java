package database;

import essence.Currency;
import essence.ExchangeRate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExchangeRatesDatabase {
    private static final String URL = "jdbc:sqlite:C:/SQLLite/database1/database1.db";

    public static List<ExchangeRate> getAllExchangeRates() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "select exc.id, cur1.id as baseId, cur1.code as baseCode, cur1.fullname as baseName, \n" +
                "cur1.sign as baseSign, cur2.id as targetId, cur2.code as targetCode, cur2.fullname as targetName, cur2.sign as targetSign, exc.rate\n" +
                "from ExchangeRates exc\n" +
                "join Currencies cur1 on exc.BaseCurrencyId=cur1.id\n" +
                "join Currencies cur2 on exc.TargetCurrencyId=cur2.id;";
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            exchangeRateList.add(exchangeRateByQuery(rs));
        }
        return exchangeRateList;
    }

    private static ExchangeRate exchangeRateByQuery(ResultSet rs) throws SQLException{
        Integer id = rs.getInt("id");
        Integer baseId = rs.getInt("baseId");
        String baseCode = rs.getString("baseCode");
        String baseName = rs.getString("baseName");
        String baseSign = rs.getString("baseSign");
        Currency baseCurrency = new Currency(baseId, baseName, baseCode, baseSign);
        Integer targetId = rs.getInt("targetId");
        String targetCode = rs.getString("targetCode");
        String targetName = rs.getString("targetName");
        String targetSign = rs.getString("targetSign");
        Currency targetCurrency = new Currency(targetId, targetName, targetCode, targetSign);
        Double rate = rs.getDouble("rate");
        return new ExchangeRate(id, baseCurrency, targetCurrency, rate);
    }

    public static ExchangeRate getExchangeRateByCode(String codeBase, String codeTarget) throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = String.format("select exc.id, cur1.id as BaseId, cur1.code as baseCode, cur1.fullname as baseName, \n" +
                "cur1.sign as baseSign, cur2.id as targetId, cur2.code as targetCode, cur2.fullname as targetName, cur2.sign as targetSign, exc.rate\n" +
                "from ExchangeRates exc\n" +
                "join Currencies cur1 on exc.BaseCurrencyId=cur1.id\n" +
                "join Currencies cur2 on exc.TargetCurrencyId=cur2.id\n" +
                "where (baseCode = '%s' AND targetcode = '%s' OR targetCode = '%s' AND baseCode = '%s')", codeBase,codeTarget,codeBase,codeTarget);
        Connection connection = DriverManager.getConnection(URL);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ExchangeRate exchangeRate = exchangeRateByQuery(rs);
        connection.close();
        return exchangeRate;
    }

    public static ExchangeRate updateExchangeRate(String codeBase, String codeTarget, Double rate) throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = String.format(Locale.US,"update ExchangeRates set rate = %f\n" +
                "where BaseCurrencyId = (Select id from Currencies where code = '%s') and TargetCurrencyId = (Select id from Currencies where code = '%s')\n" +
                "or BaseCurrencyId = (Select id from Currencies where code = '%s') and TargetCurrencyId = (Select id from Currencies where code = '%s');",rate,codeBase,codeTarget,codeTarget,codeBase);

        System.out.println(sql);
        Connection connection = DriverManager.getConnection(URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        ExchangeRate exchangeRate = getExchangeRateByCode(codeBase, codeTarget);
        connection.close();
        return exchangeRate;
    }

    public static ExchangeRate insertExchangeRate(String baseCurrencyCode, String targetCurrencyCode, Double rate) throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = String.format(Locale.US,"insert into ExchangeRates (BaseCurrencyId, targetCurrencyId, Rate) \n" +
                "VALUES ((select id from Currencies where code='%s'),(select id from Currencies where code='%s'), %f );", baseCurrencyCode, targetCurrencyCode, rate);

        System.out.println(sql);
        Connection connection = DriverManager.getConnection(URL);
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        ExchangeRate exchangeRate = getExchangeRateByCode(baseCurrencyCode, targetCurrencyCode);
        connection.close();
        return exchangeRate;
    }

}
