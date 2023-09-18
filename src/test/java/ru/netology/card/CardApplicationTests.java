package ru.netology.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.card.model.card.Amount;
import ru.netology.card.model.requests.ConfirmRequestCard;
import ru.netology.card.model.requests.RequestCard;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    private String CONFIRMATION_CODE = "0000";
    private String OPERATION_ID = "1";

    private static final int PORT = 5500;
    private static final GenericContainer<?> container = new GenericContainer<>("money")
            .withExposedPorts(PORT);

    @BeforeAll
    public static void beforeAll() {
        container.start();
    }

    @Test
    public void testTrancfer() {
        String endPoint = "http://localhost:3000/transfer";
        RequestCard requestCard = new RequestCard("1234123412341234", "12/34", "123", "2345234523452345", new Amount(100, "RUR"));
        ResponseEntity<String> result = restTemplate.postForEntity(endPoint, requestCard, String.class);
        String expectdId = "{\"operationId\":\"1\"}";
        String resultId = result.getBody();
        Assertions.assertEquals(expectdId, resultId);
    }

    @Test
    public void testConfirmation() {
        String endPoint = "http://localhost:30000/confirmOperation";
        ConfirmRequestCard confirmRequestCard = new ConfirmRequestCard(OPERATION_ID, CONFIRMATION_CODE);
        ResponseEntity<String> result = restTemplate.postForEntity(endPoint, confirmRequestCard, String.class);
        String expectdId = "{\"operationId\":\"1\"}";
        String resultId = result.getBody();
        Assertions.assertEquals(expectdId, resultId);
    }


}
