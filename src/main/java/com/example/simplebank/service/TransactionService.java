package com.example.simplebank.service;

import com.example.simplebank.data.dto.TransferResponse;
import com.example.simplebank.data.entity.Card;
import com.example.simplebank.data.entity.Transaction;
import com.example.simplebank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CardService cardService;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public TransferResponse transfer(Long fromId, Long toId, BigDecimal amount) {
        Card from = cardService.find(fromId);
        Card to = cardService.find(toId);

        validateTransfer(from, amount);

        from.setAmount(from.getAmount().subtract(amount));
        to.setAmount(to.getAmount().add(amount));

        Transaction transaction = saveTransaction(from, to, amount);

        return TransferResponse.builder()
                .cardFrom(from)
                .cardTo(to)
                .transaction(transaction)
                .build();
    }

    private Transaction saveTransaction(Card from, Card to, BigDecimal amount) {
        Transaction transaction = Transaction.builder()
                .cardNumberFrom(from.getNumber())
                .cardNumberTo(to.getNumber())
                .amount(amount)
                .isSuccess(true)
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        transactionRepository.save(transaction);

        sendTransactionToTransactionHistoryService(transaction);

        return transaction;
    }

    private void sendTransactionToTransactionHistoryService(Transaction transaction) {
        HttpEntity<Transaction> requestEntity = new HttpEntity<>(transaction);
        restTemplate.exchange("http://localhost:1002/transactions/history",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }

    private void validateTransfer(Card from, BigDecimal amount) {
        //TODO check if BigDecimal .setScale(2, RoundingMode.CEILING)
        BigDecimal subtract = from.getAmount().subtract(amount);
        int compareTo = subtract.compareTo(new BigDecimal(0));
        if (compareTo < 0) throw new RuntimeException("Insufficient funds on the account. Card amount ["
                + from.getAmount() + "], amount [" + amount + "]");
    }

}
