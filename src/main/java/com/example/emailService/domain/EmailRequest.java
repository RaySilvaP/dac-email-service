package com.example.emailService.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmailRequest {
    private String Address;
    private String userName;
    private String eventName;
    private String ticketType;
    private Integer quantity;
    private double totalPrice;
}
