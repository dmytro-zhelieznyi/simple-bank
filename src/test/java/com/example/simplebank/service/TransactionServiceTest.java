package com.example.simplebank.service;

import com.example.simplebank.data.dto.TransferResponse;
import com.example.simplebank.data.entity.Card;
import com.example.simplebank.data.entity.Transaction;
import com.example.simplebank.repository.CardRepository;
import com.example.simplebank.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:db/scripts/transaction-service-data-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/scripts/transaction-service-data-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void transferSuccessWhenCardFromAmountHigherThanAmount() {
        TransferResponse transfer = transactionService.transfer(1L, 2L,
                new BigDecimal(5_000).setScale(2, RoundingMode.CEILING));
        Card from = cardRepository.findById(1L).get();
        Card to = cardRepository.findById(2L).get();
        Transaction transaction = transactionRepository.findById(transfer.getTransaction().getId()).get();

        assertEquals(new BigDecimal(5_000).setScale(2, RoundingMode.CEILING), from.getAmount());
        assertEquals(new BigDecimal(15_000).setScale(2, RoundingMode.CEILING), to.getAmount());
        assertEquals(transfer.getTransaction(), transaction);
    }

    @Test
    public void transferFailedWhenCardFromAmountLessThanAmount() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            transactionService.transfer(1L, 2L, new BigDecimal(75_000).setScale(2, RoundingMode.CEILING));
        });

        assertEquals("Insufficient funds on the account. Card amount [10000.00], amount [75000.00]",
                runtimeException.getMessage());
    }

}
