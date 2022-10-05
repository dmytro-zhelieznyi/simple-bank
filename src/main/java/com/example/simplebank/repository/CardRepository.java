package com.example.simplebank.repository;

import com.example.simplebank.data.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
