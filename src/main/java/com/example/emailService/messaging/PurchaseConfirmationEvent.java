package com.example.emailService.messaging;

import java.io.Serializable;

public record PurchaseConfirmationEvent(String emailAddress, String userName, String eventName, String ticketType, Integer quantity, double totalPrice) implements Serializable {
}
