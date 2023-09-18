package ru.netology.card.service;

import ru.netology.card.model.card.Card;
import ru.netology.card.model.excepsions.ErrorInputData;
import ru.netology.card.model.excepsions.ErrorTransfer;
import ru.netology.card.model.requests.ConfirmRequestCard;
import ru.netology.card.model.requests.RequestCard;
import ru.netology.card.model.responces.ConfirmResponceCard;
import ru.netology.card.model.responces.ResponceCard;

public interface CardService {
    ResponceCard transfer(RequestCard card);

    ConfirmResponceCard confirmOperation(ConfirmRequestCard card);

    void checkCardBalance(Card card) throws ErrorTransfer;

    void checkCardNumber(Card card) throws ErrorInputData;

    void checkCvv(Card card) throws ErrorInputData;
}
