package polihack.backend.dto.request;

import lombok.Data;
import polihack.backend.model.enums.Currency;

@Data
public class PaymentRequest {
    private Long amount;      // Suma (ex: 50)
    private Currency currency; // Enum: RON, EUR, USD
}