package ru.netology.card.model.card;

public record Card(String number, String validTill, String cvv, Amount sum) {


    public Card(String number, Amount sum) {
        this(number, null, null, sum);
    }
}
