package com.example.simplebank.api.controller;

import com.example.simplebank.data.dto.TransferResponse;
import com.example.simplebank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PutMapping
    public TransferResponse transfer(@RequestParam Long fromId,
                                     @RequestParam Long toId,
                                     @RequestParam BigDecimal amount) {
        return transactionService.transfer(fromId, toId, amount);
    }

}
