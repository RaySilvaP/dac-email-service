package com.example.emailService.messaging;

import java.io.Serializable;
import java.math.BigDecimal;

public record PurchaseConfirmationEvent(String emailAddress, String userName, BigDecimal totalPrice) implements Serializable {
}
