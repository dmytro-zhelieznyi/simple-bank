package com.example.simplebank.service;

import com.example.simplebank.data.entity.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CardService cardService;

    @Transactional
    public List<Card> transfer(Long fromId, Long toId, BigDecimal amount) {
        Card from = cardService.find(fromId);
        Card to = cardService.find(toId);

        validateTransfer(from, to, amount);

        from.setAmount(from.getAmount().subtract(amount));
        to.setAmount(to.getAmount().add(amount));

        return List.of(from, to);
    }

    private boolean validateTransfer(Card from, Card to, BigDecimal amount) {
        BigDecimal subtract = from.getAmount().subtract(amount);
        int compareTo = subtract.compareTo(new BigDecimal(0));
        if (compareTo < 0) throw new RuntimeException("Insufficient funds on the account. Card amount [" 
                + from.getAmount() + "], amount [" + amount + "]");
        return true;
    }

}
