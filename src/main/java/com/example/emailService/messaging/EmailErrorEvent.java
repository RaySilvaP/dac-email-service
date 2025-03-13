package com.example.emailService.messaging;

import java.io.Serializable;

public record EmailErrorEvent(String emailAddress, String message) implements Serializable {
}
