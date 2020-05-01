package com.carwash.server.dto.payments;

import com.carwash.server.models.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeOrderPay {
    private OrderType orderType;
    private Long id;
}
