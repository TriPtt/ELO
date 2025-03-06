package fr.ynov.ELO.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Capture les erreurs de conversion Enum (ex: "type" invalide)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidEnumValue(InvalidFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Type d'anecdote invalide. Valeurs acceptées : [DROLE, HISTORIQUE, SCIENTIFIQUE]"));
    }
}
