package ru.netology.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.card.model.card.Amount;
import ru.netology.card.model.requests.RequestCard;
import ru.netology.card.repository.CardRepositoryImpl;

public class TestRepository {
    private final CardRepositoryImpl cardRepository = new CardRepositoryImpl();
    private final String OPERATION_ID = "1";
    private final String CARD_FROM_NUMBER = "1234123412341234";
    private final String CARD_FROM_VALID_TILL = "12/34";
    private final String CARD_FROM_CVV = "123";
    private final String CARD_TO_NUMBER = "2345234523452345";
    private final Amount AMOUNT = new Amount(1, "RUR");

    @Test
    public void testSaveOperation() {
        RequestCard requestCard = new RequestCard(CARD_FROM_NUMBER, CARD_FROM_VALID_TILL, CARD_FROM_CVV, CARD_TO_NUMBER, AMOUNT);
        cardRepository.saveOperatoin(OPERATION_ID, requestCard);
        RequestCard result = cardRepository.getOperations().get(OPERATION_ID);
        RequestCard expected = cardRepository.getOperations().get(OPERATION_ID);
        Assertions.assertEquals(expected, result);
    }
}
