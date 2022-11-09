package com.equitasitinc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.equitasitinc.dto.StatusDTO;
import com.equitasitinc.exception.AccountException;

@ControllerAdvice
@Slf4j
public class AccountExceptionController {

    @ExceptionHandler(value = AccountException.class)
    public ResponseEntity handleAccountException(AccountException ex) {
        log.error("Error while getting the accounts",ex);
        return new ResponseEntity<>(new StatusDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleException(Exception ex) {
        log.error("Error while accessing the accounts app",ex);
        return new ResponseEntity<>(new StatusDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
