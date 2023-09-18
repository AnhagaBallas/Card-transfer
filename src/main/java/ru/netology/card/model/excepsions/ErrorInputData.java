package ru.netology.card.model.excepsions;

public class ErrorInputData extends Exception {
    public ErrorInputData(String message, Integer id) {
        super(message);
    }
}
