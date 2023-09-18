package ru.netology.card;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.netology.card.model.excepsions.ErrorConfirmation;
import ru.netology.card.model.excepsions.ErrorInputData;
import ru.netology.card.model.excepsions.ErrorTransfer;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<ErrorConfirmation> errorConfirmationHandler(ErrorConfirmation errorConfirmation) {
        return ResponseEntity.internalServerError().body(errorConfirmation);

    }

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ErrorInputData> errorInputDataHandler(ErrorInputData errorInputData) {
        return ResponseEntity.badRequest().body(errorInputData);
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<ErrorTransfer> errorTrancferHandler(ErrorTransfer errorTransfer) {
        return ResponseEntity.internalServerError().body(errorTransfer);
    }

}
