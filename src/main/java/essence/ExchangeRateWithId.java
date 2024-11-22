package essence;

public class ExchangeRateWithId {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public Integer getCurrencyBaseId() {
        return currencyBaseId;
    }

    public Integer getCurrencyTargetId() {
        return currencyTargetId;
    }

    public Double getRate() {
        return rate;
    }

    private Integer currencyBaseId;
    private Integer currencyTargetId;
    private Double rate;

    public ExchangeRateWithId(Integer id, Integer currencyBaseId, Integer currencyTargetId, Double rate) {
        this.id = id;
        this.currencyBaseId = currencyBaseId;
        this.currencyTargetId = currencyTargetId;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return String.format("id: %d\n%s\n%s\n%.5f", id, currencyBaseId, currencyTargetId, rate);
    }

}
