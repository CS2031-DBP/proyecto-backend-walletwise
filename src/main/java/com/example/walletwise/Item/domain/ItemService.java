package com.example.walletwise.Item.domain;

import com.example.walletwise.Item.dtos.ItemDTO;
import com.example.walletwise.Item.infrastructure.ItemRepository;
import com.example.walletwise.Transaccion.domain.Transaccion;
import com.example.walletwise.Transaccion.infrastructure.TransaccionRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private TransaccionRepository transaccionRepository;
    @Autowired
    private ItemRepository itemRepository;

    public ItemDTO crearItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setNombre(itemDTO.getNombre());
        item.setPrecio(itemDTO.getPrecio());
        item.setDescripcion(itemDTO.getDescripcion()); // Asignar la descripción

        // Asignar la transacción
        Transaccion transaccion = transaccionRepository.findById(itemDTO.getTransaccionId())
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + itemDTO.getTransaccionId()));
        item.setTransaccion(transaccion);

        itemRepository.save(item);
        return mapToDTO(item);
    }
    public List<ItemDTO> obtenerTodosLosItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ItemDTO obtenerItemPorId(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado con id: " + id));
        return mapToDTO(item);
    }

    public ItemDTO actualizarItem(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado con id: " + id));

        item.setNombre(itemDTO.getNombre());
        item.setPrecio(itemDTO.getPrecio());
        item.setDescripcion(itemDTO.getDescripcion()); // Actualizar descripción

        // Actualizar la transacción si se proporciona un nuevo transaccionId
        if (itemDTO.getTransaccionId() != null) {
            Transaccion transaccion = transaccionRepository.findById(itemDTO.getTransaccionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + itemDTO.getTransaccionId()));
            item.setTransaccion(transaccion);
        }

        itemRepository.save(item);
        return mapToDTO(item);
    }

    public void eliminarItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado con id: " + id));
        itemRepository.delete(item);
    }
    private ItemDTO mapToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setNombre(item.getNombre());
        itemDTO.setPrecio(item.getPrecio());
        itemDTO.setDescripcion(item.getDescripcion()); // Asignar descripción
        itemDTO.setTransaccionId(item.getTransaccion().getId()); // Asignar transaccionId
        return itemDTO;
    }

}

