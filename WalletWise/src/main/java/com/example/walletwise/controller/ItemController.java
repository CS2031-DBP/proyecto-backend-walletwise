package com.example.walletwise.controller;

import com.example.walletwise.dto.ItemDTO;
import com.example.walletwise.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDTO> crearItem(@RequestBody ItemDTO itemDTO) {
        return new ResponseEntity<>(itemService.crearItem(itemDTO), HttpStatus.CREATED);
    }
}

