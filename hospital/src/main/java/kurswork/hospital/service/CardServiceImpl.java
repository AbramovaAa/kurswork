package kurswork.hospital.service;
import kurswork.hospital.dto.CardDto;
import kurswork.hospital.entity.Card;
import kurswork.hospital.repository.CardRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    private CardRepository cardRepository;
    public CardServiceImpl(CardRepository cardRepository){
        this.cardRepository=cardRepository;
    }

    @Override
    public Card saveCard(CardDto cardDto) {
        Card card=new Card();
        card.setData(cardDto.getData());
        card.setFathersName(cardDto.getFathersName());
        card.setFirstName(cardDto.getFirstName());
        card.setLastName(cardDto.getLastName());
        card.setNumber(cardDto.getNumber());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameCreator= authentication.getName();
        card.setNameCreator(nameCreator);
        return cardRepository.save(card);
    }

    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public List<CardDto> findAllCards() {
        List<Card> cards=cardRepository.findAll();
        return cards.stream().map((card) -> convertEntityToDto(card))
                .collect(Collectors.toList());
    }
    private CardDto convertEntityToDto(Card card){
        CardDto cardDto = new CardDto();
        cardDto.setFirstName(card.getFirstName());
        cardDto.setLastName(card.getLastName());
        cardDto.setFathersName(card.getFathersName());
        cardDto.setNumber(card.getNumber());
        cardDto.setId(card.getId());
        cardDto.setData(card.getData());
        cardDto.setNameCreator(card.getNameCreator());
        return cardDto;
    }
}
