package ru.netology.card.repository;

import ru.netology.card.model.requests.RequestCard;

public interface CardRepository {
    void saveOperatoin(String operationId, RequestCard requestCard);

    String getId();
}
