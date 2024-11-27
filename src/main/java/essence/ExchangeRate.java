package essence;

public class ExchangeRate {
    private Integer id;
    private Currency currencyBase;

    public void setCurrencyBase(Currency currencyBase) {
        this.currencyBase = currencyBase;
    }

    public void setCurrencyTarget(Currency currencyTarget) {
        this.currencyTarget = currencyTarget;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    private Currency currencyTarget;
    private Double rate;

    public ExchangeRate(Integer id, Currency currencyBase, Currency currencyTarget, Double rate) {
        this.id = id;
        this.currencyBase = currencyBase;
        this.currencyTarget = currencyTarget;
        this.rate = rate;
    }

    public Currency getCurrencyBase() {
        return currencyBase;
    }

    public Currency getCurrencyTarget() {
        return currencyTarget;
    }

    public Double getRate() {
        return rate;
    }


    @Override
    public String toString() {
        return String.format("id: %d\n%s\n%s\n%.5f", id, currencyBase, currencyTarget, rate);
    }
}
