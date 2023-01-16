package kurswork.hospital.controller;
import jakarta.validation.Valid;
import kurswork.hospital.dto.CardDto;
import kurswork.hospital.dto.UserDto;
import kurswork.hospital.entity.Card;
import kurswork.hospital.entity.User;
import kurswork.hospital.repository.CardRepository;
import kurswork.hospital.service.CardService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CardController {
    private CardService cardService;
    private CardRepository cardRepository;

    public CardController(CardRepository cardRepository,CardService cardService){
        this.cardRepository=cardRepository;
        this.cardService=cardService;
    }

    @GetMapping("doctor")
    public String doctor(Model model){
        List<CardDto> cards=cardService.findAllCards();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameCreator= authentication.getName();
        List<CardDto> my_cards=new ArrayList<>();
        for(CardDto cardDto:cards){
            if(cardDto.getNameCreator().equals(nameCreator)){
                my_cards.add(cardDto);
            }
        }
        model.addAttribute("cards",my_cards);
        return "doctor";
    }

    @GetMapping("chief")
    public String chief(Model model){
        List<CardDto> cards=cardService.findAllCards();
        model.addAttribute("cards",cards);
        return "chief";
    }
    @GetMapping("doctor/new")
    public String dNewCard(Model model){
        CardDto cardDto=new CardDto();
        model.addAttribute("card",cardDto);
        return "new_card";
    }

    @PostMapping("/doctor/new/save")
    public String dNewSave(@Valid @ModelAttribute("card") CardDto card,
                               BindingResult result,
                               Model model){
        Card existing = cardService.findByNumber(card.getNumber());
        if (existing != null) {
            result.rejectValue("number", null, "There is already a card with that number");
        }
        if (result.hasErrors()) {
            model.addAttribute("card", card);
        }
        cardService.saveCard(card);
        return "redirect:/doctor";
    }
    @GetMapping("chief/new")
    public String cNewCard(Model model){
        CardDto cardDto=new CardDto();
        model.addAttribute("card",cardDto);
        return "new_card_c";
    }
    @PostMapping("/chief/new/save")
    public String cNewSave(@Valid @ModelAttribute("card") CardDto card,
                               BindingResult result,
                               Model model){
        Card existing = cardService.findByNumber(card.getNumber());
        if (existing != null) {
            result.rejectValue("number", null, "There is already a card with that number");
        }
        if (result.hasErrors()) {
            model.addAttribute("card", card);
        }
        cardService.saveCard(card);
        return "redirect:/chief";
    }

    @GetMapping("chief/edit/{card}")
    public String cEditCard(@PathVariable Card card, Model model){
        model.addAttribute("card",card);
        return "edit_card_c";
    }

    @PostMapping("chief/edit/{card}")
    public String cEditSave(@PathVariable Card card,@RequestParam String firstName,
                            @RequestParam String lastName,@RequestParam String fathersName,
                            @RequestParam String data, @RequestParam String number){
        card.setNumber(number);
        card.setLastName(lastName);
        card.setFirstName(firstName);
        card.setFathersName(fathersName);
        card.setData(data);
        cardRepository.save(card);
        return "redirect:/chief";
    }

    @GetMapping("doctor/edit/{card}")
    public String dEditCard(@PathVariable Card card, Model model){
        model.addAttribute("card",card);
        return "edit_card";
    }

    @PostMapping("doctor/edit/{card}")
    public String dEditSave(@PathVariable Card card,@RequestParam String firstName,
                            @RequestParam String lastName,@RequestParam String fathersName,
                            @RequestParam String data, @RequestParam String number){
        card.setNumber(number);
        card.setLastName(lastName);
        card.setFirstName(firstName);
        card.setFathersName(fathersName);
        card.setData(data);
        cardRepository.save(card);
        return "redirect:/doctor";
    }
    @GetMapping("doctor/{card}")
    public String dDelete(@PathVariable Card card){
        cardRepository.delete(card);
        return "redirect:/doctor";
    }
    @GetMapping("chief/{card}")
    public String cDelete(@PathVariable Card card){
        cardRepository.delete(card);
        return "redirect:/chief";
    }
}
