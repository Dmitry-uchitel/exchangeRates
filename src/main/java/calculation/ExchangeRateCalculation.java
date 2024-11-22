package calculation;

import database.ExchangeRatesDatabase;
import essence.Currency;
import essence.ExchangeRate;

public class ExchangeRateCalculation {

    private Currency currencyBase;
    private Currency currencyTarget;
    private Double rate;
    private Double amount;
    private Double convertedAmount;

    public ExchangeRateCalculation(Currency currencyBase, Currency currencyTarget, Double rate, Double amount, Double convertedAmount) {
        this.currencyBase = currencyBase;
        this.currencyTarget = currencyTarget;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%.5f\n%.2f\n%.2f", currencyBase, currencyTarget, rate,amount,convertedAmount);
    }

    public static ExchangeRateCalculation calculate(String codeBase, String codeTarget, Double amount){
        ExchangeRate exchangeRate = ExchangeRatesDatabase.getExchangeRateByCode(codeBase, codeTarget);
        Currency currencyBase;
        Currency currencyTarget;
        Double rate;
        if (codeBase.equals(exchangeRate.getCurrencyBase().getCode())){
            currencyBase = exchangeRate.getCurrencyBase();
            currencyTarget = exchangeRate.getCurrencyTarget();
            rate = exchangeRate.getRate();
        }
        else {
            currencyBase = exchangeRate.getCurrencyTarget();
            currencyTarget = exchangeRate.getCurrencyBase();
            rate = 1/exchangeRate.getRate();
        }
        Double convertedAmount = amount*rate;
        return new ExchangeRateCalculation(currencyBase, currencyTarget, rate, amount, convertedAmount);
    }
}
