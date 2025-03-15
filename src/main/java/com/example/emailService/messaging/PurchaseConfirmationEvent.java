package com.example.emailService.messaging;

import com.example.emailService.dto.PurchaseItemEmailDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public record PurchaseConfirmationEvent(
        String emailAddress,
        String userName,
        Set<PurchaseItemEmailDto> purchaseItems,
        BigDecimal totalPrice
) implements Serializable { }
