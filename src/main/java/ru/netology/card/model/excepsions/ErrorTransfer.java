package ru.netology.card.model.excepsions;

public class ErrorTransfer extends Exception {
    public ErrorTransfer(String message, Integer id) {
        super(message);
    }
}
