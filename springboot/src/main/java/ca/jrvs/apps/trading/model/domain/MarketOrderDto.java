package ca.jrvs.apps.trading.model.domain;

public class MarketOrderDto {
    private Integer accountId;
    private Integer size;
    private String ticker;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public String toString() {
        return "MarketOrderDto{" +
                "accountId=" + accountId +
                ", size=" + size +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
