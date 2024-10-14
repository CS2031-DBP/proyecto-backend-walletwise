package com.example.walletwise.email;

import com.example.walletwise.Presupuesto.domain.Presupuesto;
import com.example.walletwise.Presupuesto.infrastructure.PresupuestoRepository;
import com.example.walletwise.Transaccion.domain.Transaccion;
import com.example.walletwise.events.TransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EmailListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @EventListener
    @Async
    public void handleTransactionEvent(TransactionEvent event) {
        Transaccion transaccion = event.getTransaccion();
        String email = event.getEmail();

        // Obtener el presupuesto relevante (puedes ajustar esto según tu lógica)
        Presupuesto presupuesto = presupuestoRepository.findByUsuarioIdAndCategoriaId(
                transaccion.getCuenta().getUsuario().getId(),
                transaccion.getCategoria().getId()
        ).stream().findFirst().orElse(null);

        // Generar el cuerpo del correo electrónico
        String htmlBody = generateHtmlBody(transaccion, presupuesto);
        emailService.sendHtmlMessage(email, "Actualización de Presupuesto y Transacción", htmlBody);
    }

    private String generateHtmlBody(Transaccion transaccion, Presupuesto presupuesto) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n")
                .append("<head>\n")
                .append("    <meta charset=\"UTF-8\">\n")
                .append("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n")
                .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
                .append("    <title>Actualización de Presupuesto</title>\n")
                .append("    <style>\n")
                .append("        body, h1, p, ul {\n")
                .append("            margin: 0;\n")
                .append("            padding: 0;\n")
                .append("        }\n")
                .append("        body {\n")
                .append("            font-family: Arial, sans-serif;\n")
                .append("            background-color: #f4f4f4;\n")
                .append("            padding: 20px;\n")
                .append("        }\n")
                .append("        .container {\n")
                .append("            max-width: 600px;\n")
                .append("            margin: 0 auto;\n")
                .append("            background-color: #fff;\n")
                .append("            padding: 20px;\n")
                .append("            border-radius: 10px;\n")
                .append("            box-shadow: 0 0 10px rgba(0,0,0,0.1);\n")
                .append("        }\n")
                .append("        h1 {\n")
                .append("            color: #333;\n")
                .append("            text-align: center;\n")
                .append("            margin-bottom: 20px;\n")
                .append("        }\n")
                .append("        p {\n")
                .append("            color: #666;\n")
                .append("            font-size: 16px;\n")
                .append("            line-height: 1.6;\n")
                .append("            margin-bottom: 10px;\n")
                .append("        }\n")
                .append("        .alert {\n")
                .append("            color: red;\n")
                .append("            font-weight: bold;\n")
                .append("        }\n")
                .append("    </style>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("    <div class=\"container\">\n")
                .append("        <h1>Actualización de Transacción</h1>\n")
                .append("        <p>Has registrado una nueva transacción:</p>\n")
                .append("        <ul>\n")
                .append("            <li><strong>Monto:</strong> ").append(transaccion.getMonto()).append("</li>\n")
                .append("            <li><strong>Destinatario:</strong> ").append(transaccion.getDestinatario()).append("</li>\n")
                .append("            <li><strong>Fecha:</strong> ").append(transaccion.getFecha()).append("</li>\n")
                .append("            <li><strong>Tipo:</strong> ").append(transaccion.getTipo()).append("</li>\n")
                .append("        </ul>\n");

        // Obtener el saldo disponible actualizado de la cuenta
        BigDecimal saldoDisponible = transaccion.getCuenta().getSaldo();
        sb.append("        <p><strong>Saldo disponible en la cuenta:</strong> ").append(saldoDisponible).append("</p>\n");

        if (presupuesto != null) {
            BigDecimal saldoPresupuesto = presupuesto.getMontoTotal().subtract(presupuesto.getGastoActual());
            sb.append("        <p><strong>Presupuesto para la categoría:</strong> ").append(presupuesto.getCategoria().getNombre()).append("</p>\n")
                    .append("        <ul>\n")
                    .append("            <li><strong>Monto Total:</strong> ").append(presupuesto.getMontoTotal()).append("</li>\n")
                    .append("            <li><strong>Gasto Actual:</strong> ").append(presupuesto.getGastoActual()).append("</li>\n")
                    .append("            <li><strong>Saldo Disponible:</strong> ").append(saldoPresupuesto).append("</li>\n")
                    .append("        </ul>\n");

            if (presupuesto.getGastoActual().compareTo(presupuesto.getMontoTotal()) > 0) {
                sb.append("        <p class=\"alert\">¡Has superado tu presupuesto para esta categoría!</p>\n");
            } else if (presupuesto.getGastoActual().compareTo(presupuesto.getMontoTotal()) == 0) {
                sb.append("        <p class=\"alert\">Has alcanzado exactamente tu presupuesto para esta categoría.</p>\n");
            } else if (presupuesto.getGastoActual().compareTo(presupuesto.getMontoTotal().multiply(new BigDecimal("0.8"))) > 0) {
                sb.append("        <p class=\"alert\">Estás cerca de alcanzar tu presupuesto para esta categoría.</p>\n");
            }

        }

        sb.append("        <p>¡Mantén un buen manejo de tus finanzas!</p>\n")
                .append("    </div>\n")
                .append("</body>\n")
                .append("</html>\n");

        return sb.toString();
    }
}
