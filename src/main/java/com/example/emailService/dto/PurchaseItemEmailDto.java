package com.example.emailService.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record PurchaseItemEmailDto(
     String eventName,
     Integer quantity,
     BigDecimal price
) implements Serializable {}
