package com.example.walletwise.Item.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDTO {
    private Long id;
    private String nombre;       // Nombre del ítem, por ejemplo, "Supermercado"
    private BigDecimal precio;   // Precio del ítem
    private Long transaccionId;  // Referencia a la transacción donde está incluido el ítem
}

