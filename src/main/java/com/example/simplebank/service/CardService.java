package com.example.simplebank.service;

import com.example.simplebank.data.entity.Card;
import com.example.simplebank.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    @PostConstruct
    public void postConstructor() {
        // TODO change with liquibase change sets
        List<Card> cards = Stream.generate(this::createCard)
                .limit(2)
                .toList();

        cards.forEach(card -> {
            card.setAmount(new BigDecimal(10_000));
            updateCard(card);
        });
    }

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
        for (int i = 0; i < 16; i++) {
            number.append(random.nextInt(9) + 1);
        }

        // TODO create validator to check if number is unique
        return number.toString();
    }

}
