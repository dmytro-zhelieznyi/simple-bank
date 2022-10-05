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

    public Card createCard() {
        Card card = Card.builder()
                .id(null)
                .number(generateUniqueCardNumber())
                .amount(new BigDecimal(0))
                .build();

        return cardRepository.save(card);
    }

    private String generateUniqueCardNumber() {
        Random random = new Random();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            number.append(random.nextInt(9) + 1);
        }

        // TODO validator to check if number is unique
        return number.toString();
    }

}
