package com.example.walletwise.events;

import com.example.walletwise.Transaccion.domain.Transaccion;
import com.example.walletwise.email.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventListener {
/*
    private final EmailService emailService;

    public TransactionEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleTransactionEvent(TransactionEvent event) {
        Transaccion transaccion = event.getTransaccion();
        String userEmail = transaccion.getCuenta().getUsuario().getEmail(); // Obtener el email del usuario asociado

        System.out.println("Transacción registrada: " + transaccion.getDescripcion());

        // Enviar correo de notificación al usuario correspondiente
        emailService.sendTransactionNotification(
                userEmail,  // Correo dinámico del usuario
                "Nueva Transacción",
                "Se ha registrado una nueva transacción: " + transaccion.getDescripcion()
        );
    }*/
}