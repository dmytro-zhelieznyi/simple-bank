package com.example.simplebank.service;

import com.example.simplebank.data.entity.Card;
import com.example.simplebank.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public Card find(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card with id [" + id + "] doesn't exist"));
    }

    public Card createCard() {
        Card card = Card.builder()
                .id(null)
                .number(generateUniqueCardNumber())
                .amount(new BigDecimal(0))
                .build();

        return cardRepository.save(card);
    }

    public Card updateCard(Card card) {
        return cardRepository.save(card);
    }

    private String generateUniqueCardNumber() {
        Random random = new Random();
        StringBuilder number = new StringBuilder();

        number.append(random.nextInt(9) + 1);
        for (int i = 0; i < 15; i++) {
            number.append(random.nextInt(10));
        }

        if (isCardNumberUnique(number.toString())) {
            return number.toString();
        }

        return generateUniqueCardNumber();
    }

    private boolean isCardNumberUnique(String number) {
        return cardRepository.findByNumber(number).isEmpty();
    }

}
