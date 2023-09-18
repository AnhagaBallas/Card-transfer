package ru.netology.card.repository;

import org.springframework.stereotype.Repository;
import ru.netology.card.model.requests.RequestCard;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CardRepositoryImpl implements CardRepository {
    private Map<String, RequestCard> operations = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger();

    @Override
    public void saveOperatoin(String operationId, RequestCard requestCard) {
        operations.put(operationId, requestCard);
    }

    public Map<String, RequestCard> getOperations() {
        return operations;
    }

    @Override
    public String getId() {
        return String.valueOf(id.incrementAndGet());

    }

}
