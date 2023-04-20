package ca.jrvs.apps.trading.model.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountId",
        "id",
        "notes",
        "price",
        
})
public class SecurityOrder implements Entity<Integer>{
    public enum Status {
        FILLED, CANCELED, PENDING
    }

    private Integer accountId;
    private Integer id;
    private String notes;
    private Double price;
    private Integer size;
    private Status status;
    private String ticker;

}
