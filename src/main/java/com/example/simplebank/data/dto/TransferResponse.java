package com.example.simplebank.data.dto;

import com.example.simplebank.data.entity.Card;
import com.example.simplebank.data.entity.Transaction;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class TransferResponse {

    private Card cardFrom;
    private Card cardTo;
    private Transaction transaction;

}
