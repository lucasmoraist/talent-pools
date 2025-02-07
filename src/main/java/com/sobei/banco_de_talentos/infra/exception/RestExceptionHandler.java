package com.sobei.banco_de_talentos.infra.exception;

import com.sobei.banco_de_talentos.domain.dto.DataException;
import com.sobei.banco_de_talentos.domain.dto.ExceptionDTO;
import com.sobei.banco_de_talentos.domain.exceptions.CredentialsInvalid;
import com.sobei.banco_de_talentos.domain.exceptions.ResourceNotFound;
import com.sobei.banco_de_talentos.domain.exceptions.UniqueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    protected ResponseEntity<ExceptionDTO> handleEntityNotFoundException(ResourceNotFound ex) {
        log.warn("Entity not found");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND
                )
        );
    }

    @ExceptionHandler(UniqueException.class)
    protected ResponseEntity<ExceptionDTO> handleUniqueException(UniqueException ex) {
        log.warn("Unique constraint violation");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST
                )
        );
    }

    @ExceptionHandler(CredentialsInvalid.class)
    protected ResponseEntity<ExceptionDTO> handleCredentialsInvalid(CredentialsInvalid ex) {
        log.warn("Credentials invalid");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<List<DataException>> handleDataRequestException(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors()
                .stream()
                .map(DataException::new)
                .toList();
        log.warn("Data request exception");

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionDTO> handleException(Exception ex) {
        log.error("Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
        );
    }

}
