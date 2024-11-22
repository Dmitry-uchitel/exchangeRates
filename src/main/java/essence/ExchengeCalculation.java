package essence;

public class ExchengeCalculation {
    private Currency currencyBase;
    private Currency currencyTarget;
    private Double rate;
    private double amount;
    private double convertedAmount;

    public ExchengeCalculation(Currency currencyBase, Currency currencyTarget, Double rate, double amount, double convertedAmount) {
        this.currencyBase = currencyBase;
        this.currencyTarget = currencyTarget;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

}
