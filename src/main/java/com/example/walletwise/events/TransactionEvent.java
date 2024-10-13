package com.example.walletwise.events;

import com.example.walletwise.Transaccion.domain.Transaccion;
import org.springframework.context.ApplicationEvent;

public class TransactionEvent extends ApplicationEvent {
    private final String email;
    private final Transaccion transaccion;

    public TransactionEvent(Object source, String email, Transaccion transaccion) {
        super(source);
        this.email = email;
        this.transaccion = transaccion;
    }

    public String getEmail() {
        return email;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }
}


