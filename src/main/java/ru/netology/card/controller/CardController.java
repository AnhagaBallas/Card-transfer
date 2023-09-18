package ru.netology.card.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.card.logging.ApplicationLogger;
import ru.netology.card.model.requests.ConfirmRequestCard;
import ru.netology.card.model.requests.RequestCard;
import ru.netology.card.model.responces.ConfirmResponceCard;
import ru.netology.card.model.responces.ResponceCard;
import ru.netology.card.service.CardServiceImpl;

@RestController
@RequiredArgsConstructor
public class CardController {
    @NonNull
    private final CardServiceImpl cardServiceImpl;
    private final Logger LOGGER = ApplicationLogger.getLogger();


    @SneakyThrows
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody RequestCard card) {
        LOGGER.info("Получен запрос на перевод денежных средств");
        ResponceCard responceCard = cardServiceImpl.transfer(card);
        return new ResponseEntity<>(responceCard, HttpStatusCode.valueOf(200));
    }

    @SneakyThrows
    @PostMapping("/confirmOperation")
    public ResponseEntity<?> confirmOperation(@RequestBody ConfirmRequestCard card) {
        LOGGER.info("Запрос обработан");
        ConfirmResponceCard confirmResponceCard = cardServiceImpl.confirmOperation(card);
        return new ResponseEntity<>(confirmResponceCard, HttpStatusCode.valueOf(200));
    }


}
