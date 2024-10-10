package com.example.walletwise.Item.application;

import com.example.walletwise.Item.dtos.ItemDTO;
import com.example.walletwise.Item.domain.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ItemDTO> crearItem(@RequestBody ItemDTO itemDTO) {
        return new ResponseEntity<>(itemService.crearItem(itemDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ItemDTO>> listarTodosLosItems() {
        return ResponseEntity.ok(itemService.obtenerTodosLosItems());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ItemDTO> obtenerItemPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.obtenerItemPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ItemDTO> actualizarItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        return ResponseEntity.ok(itemService.actualizarItem(id, itemDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        itemService.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }
}

