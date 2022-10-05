package com.example.simplebank.data.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "number", unique = true, nullable = false, updatable = false)
    String number;

    @Column(name = "amount")
    BigDecimal amount;

}
