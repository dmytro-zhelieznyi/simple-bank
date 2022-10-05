package com.example.simplebank.repository;

import com.example.simplebank.data.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByNumber(String number);

}
