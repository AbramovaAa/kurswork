package kurswork.hospital.service;
import kurswork.hospital.dto.CardDto;
import kurswork.hospital.entity.Card;

import java.util.List;

public interface CardService {
    Card saveCard(CardDto cardDto);
    Card findByNumber(String number);
    List<CardDto> findAllCards();
}
