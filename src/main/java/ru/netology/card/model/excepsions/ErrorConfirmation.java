package ru.netology.card.model.excepsions;

public class ErrorConfirmation extends Exception {
    public ErrorConfirmation(String message, Integer id) {
        super(message);
    }
}
