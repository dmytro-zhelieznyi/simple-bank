package com.example.simplebank.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "number", unique = true, nullable = false, updatable = false)
    String number;

    @Column(name = "amount")
    Long amount;

}
