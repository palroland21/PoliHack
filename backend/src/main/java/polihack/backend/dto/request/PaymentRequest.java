package polihack.backend.dto.request;

import lombok.Data;
import polihack.backend.model.enums.Currency;

public class PaymentRequest {
    private Long amount;      // Suma (ex: 50)
    private Currency currency; // Enum: RON, EUR, USD

    public PaymentRequest(Long amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}