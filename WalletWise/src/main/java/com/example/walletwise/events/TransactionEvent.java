package com.example.walletwise.events;

import com.example.walletwise.Transaccion.domain.Transaccion;
import org.springframework.context.ApplicationEvent;

public class TransactionEvent extends ApplicationEvent {

    private final Transaccion transaccion;

    public TransactionEvent(Object source, Transaccion transaccion) {
        super(source);
        this.transaccion = transaccion;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }
}
