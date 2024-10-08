package com.example.walletwise.service;

import com.example.walletwise.domain.Item;
import com.example.walletwise.dto.ItemDTO;
import com.example.walletwise.infrastructure.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemDTO crearItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setNombre(itemDTO.getNombre());
        item.setPrecio(itemDTO.getPrecio());
        itemRepository.save(item);
        return mapToDTO(item);
    }

    private ItemDTO mapToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setNombre(item.getNombre());
        itemDTO.setPrecio(item.getPrecio());
        return itemDTO;
    }
}

