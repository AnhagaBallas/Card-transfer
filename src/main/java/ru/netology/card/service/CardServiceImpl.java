package ru.netology.card.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.netology.card.logging.ApplicationLogger;
import ru.netology.card.model.card.Amount;
import ru.netology.card.model.card.Card;
import ru.netology.card.model.excepsions.ErrorConfirmation;
import ru.netology.card.model.excepsions.ErrorInputData;
import ru.netology.card.model.excepsions.ErrorTransfer;
import ru.netology.card.model.requests.ConfirmRequestCard;
import ru.netology.card.model.requests.RequestCard;
import ru.netology.card.model.responces.ConfirmResponceCard;
import ru.netology.card.model.responces.ResponceCard;
import ru.netology.card.repository.CardRepositoryImpl;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {
    @NonNull
    private final CardRepositoryImpl cardRepositoryImpl;
    private final Logger LOGGER = ApplicationLogger.getLogger();
    private final String CONFIRMATION_CODE = "0000";
    private final int MIN_CARD_LENGTH = 16;
    private final int MIN_CVC_LENGTH = 3;

    @Override
    @SneakyThrows
    public ResponceCard transfer(RequestCard card) {
        Card cardFrom = new Card(card.cardFromNumber(), card.cardFromCVV(), card.cardFromCVV(), card.amount());
        Card cardTo = new Card(card.cardToNumber(), new Amount(2, "RUR"));
        String operationId = cardRepositoryImpl.getId();
        Integer transferAmount = card.amount().value();
        Integer cardToAmount = cardTo.sum().value();
        Integer comission = transferAmount / 100;
        cardTo = new Card(card.cardToNumber(), new Amount(((transferAmount - comission) + cardToAmount), "RUR"));
        checkCardBalance(cardFrom);
        checkCardNumber(cardFrom);
        checkCardNumber(cardTo);
        checkCvv(cardFrom);
        cardRepositoryImpl.saveOperatoin(operationId, card);

        LOGGER.info("Перевод с карты " + cardFrom.number() + " на карту " + cardTo.number() + " осуществлен.Сумма перевода "
                + transferAmount + " комиссия составила " + comission);

        return new ResponceCard(operationId);

    }

    @SneakyThrows
    @Override
    public ConfirmResponceCard confirmOperation(ConfirmRequestCard card) {
        ConfirmResponceCard confirmResponceCard = new ConfirmResponceCard(card.operationId());
        if (card.code().equals(CONFIRMATION_CODE)) {
            return confirmResponceCard;
        } else {
            ErrorConfirmation errorConfirmation = new ErrorConfirmation("Неверный код подверждения", 500);
            LOGGER.error(errorConfirmation.getMessage());
            throw errorConfirmation;
        }
    }

    @Override
    public void checkCardBalance(Card card) throws ErrorTransfer {
        if (card.sum().value() == 0) {
            ErrorTransfer errorTransfer = new ErrorTransfer("Баланс карты не должен быть равен 0", 500);
            LOGGER.error(errorTransfer.getMessage());
            throw errorTransfer;
        }
    }

    @Override
    public void checkCardNumber(Card card) throws ErrorInputData {
        if (card.number() == null) {
            ErrorInputData errorInputData = new ErrorInputData("Введите номер карты", 400);
            LOGGER.error(errorInputData.getMessage());
            throw errorInputData;
        } else if (card.number().length() < MIN_CARD_LENGTH) {
            ErrorInputData errorInputData = new ErrorInputData("Номер карты должен состоять из 16 цифр", 400);
            LOGGER.error(errorInputData.getMessage());
            throw errorInputData;
        }
    }

    @Override
    public void checkCvv(Card card) throws ErrorInputData {
        if (card.cvv().length() != MIN_CVC_LENGTH) {
            ErrorInputData errorInputData = new ErrorInputData("CVC должен состоять из 3 цифр", 400);
            LOGGER.error(errorInputData.getMessage());
            throw errorInputData;
        }
    }
}
