package ru.netology.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.card.model.card.Amount;
import ru.netology.card.model.card.Card;
import ru.netology.card.model.excepsions.ErrorInputData;
import ru.netology.card.model.excepsions.ErrorTransfer;
import ru.netology.card.model.requests.ConfirmRequestCard;
import ru.netology.card.model.requests.RequestCard;
import ru.netology.card.model.responces.ConfirmResponceCard;
import ru.netology.card.model.responces.ResponceCard;
import ru.netology.card.repository.CardRepositoryImpl;
import ru.netology.card.service.CardServiceImpl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class TestService {
    private final String OPERATION_ID = "1";
    private final String CONFIRMATION_CODE = "0000";
    private final String CARD_FROM_NUMBER = "1234123412341234";
    private final String CARD_FROM_VALID_TILL = "12/34";
    private final String CARD_FROM_CVV = "123";
    private final String CARD_TO_NUMBER = "2345234523452345";
    private final Amount AMOUNT = new Amount(1, "RUR");
    private final Amount SHORT_AMOUNT = new Amount(0, "RUR");
    private final String SHORT_CVV = "1";
    private final String SHORT_NUMBER = "1234";
    private final String NULL_NUMBER = null;
    private final CardRepositoryImpl repositoryMock = Mockito.mock(CardRepositoryImpl.class);
    private final CardServiceImpl cardServiceImpl = new CardServiceImpl(repositoryMock);

    @Test
    public void transferTest() {
        RequestCard requestCard = new RequestCard(CARD_FROM_NUMBER, CARD_FROM_VALID_TILL, CARD_FROM_CVV, CARD_TO_NUMBER, AMOUNT);
        when(repositoryMock.getId()).thenReturn(OPERATION_ID);
        doNothing().when(repositoryMock).saveOperatoin(OPERATION_ID, requestCard);
        ResponceCard expected = new ResponceCard(OPERATION_ID);
        ResponceCard result = cardServiceImpl.transfer(requestCard);
        Assertions.assertEquals(expected.operationId(), result.operationId());

    }

    @Test
    public void testConfirmation() {
        ConfirmResponceCard confirmResponceCard = new ConfirmResponceCard(OPERATION_ID);
        ConfirmRequestCard confirmRequestCard = new ConfirmRequestCard(OPERATION_ID, CONFIRMATION_CODE);
        ConfirmResponceCard expected = confirmResponceCard;
        ConfirmResponceCard result = cardServiceImpl.confirmOperation(confirmRequestCard);
        Assertions.assertEquals(expected.operationId(), result.operationId());

    }

    @Test
    public void testCheckNullCardBalance() {
        Card card = new Card(CARD_FROM_NUMBER, SHORT_AMOUNT);
        boolean result = false;
        try {
            cardServiceImpl.checkCardBalance(card);
        } catch (ErrorTransfer errorTransfer) {
            result = true;
        }
        Assertions.assertTrue(result);
    }

    @Test
    public void testCheckCvv() {
        Card card = new Card(CARD_FROM_NUMBER, CARD_FROM_VALID_TILL, SHORT_CVV, AMOUNT);
        boolean result = false;
        try {
            cardServiceImpl.checkCvv(card);
        } catch (ErrorInputData errorInputData) {
            result = true;
        }
        Assertions.assertTrue(result);
    }

    @Test
    public void testCheckCardNumber() {
        Card cardWithNullNumber = new Card(NULL_NUMBER, CARD_FROM_VALID_TILL, CARD_FROM_CVV, AMOUNT);
        boolean resultForNull = false;
        try {
            cardServiceImpl.checkCardNumber(cardWithNullNumber);
        } catch (ErrorInputData errorInputData) {
            resultForNull = true;
        }
        Assertions.assertTrue(resultForNull);
    }

    @Test
    public void testCheckShortCardNumber() {
        Card cardWithShortNumber = new Card(SHORT_NUMBER, CARD_FROM_VALID_TILL, CARD_FROM_CVV, AMOUNT);
        boolean resultForShortNumber = false;
        try {
            cardServiceImpl.checkCardNumber(cardWithShortNumber);
        } catch (ErrorInputData errorInputData) {
            resultForShortNumber = true;
        }
        Assertions.assertTrue(resultForShortNumber);

    }
}
