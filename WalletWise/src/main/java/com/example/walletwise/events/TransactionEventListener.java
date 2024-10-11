package com.example.walletwise.events;

import com.example.walletwise.email.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventListener {

    private final EmailService emailService;

    public TransactionEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleTransactionEvent(TransactionEvent event) {
        System.out.println("Transacción registrada: " + event.getTransaccion().getDescripcion());

        // Enviar correo de notificación
        emailService.sendTransactionNotification(
                "user@example.com",
                "Nueva Transacción",
                "Se ha registrado una nueva transacción: " + event.getTransaccion().getDescripcion()
        );
    }
}
