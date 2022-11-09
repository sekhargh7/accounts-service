package com.equitasitinc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equitasitinc.dto.AccountDTO;
import com.equitasitinc.dto.StatusDTO;
import com.equitasitinc.exception.AccountException;
import com.equitasitinc.service.AccountDataService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/account")
@Slf4j
@NoArgsConstructor
public class AccountController {

    @Autowired
    private AccountDataService accountDataService;

    @GetMapping
    public ResponseEntity getAllAccounts() {
        ResponseEntity responseEntity;
        log.debug("getAllAccounts start");
        List<AccountDTO> accountDTOList = accountDataService.getAllAccount();
        responseEntity = ResponseEntity.ok(accountDTOList);
        log.info("accountDTOList size = {}", accountDTOList.size());
        log.debug("getAllAccounts end");
        return responseEntity;
    }

    @GetMapping("{accountId}")
    public ResponseEntity getAccount(@PathVariable("accountId") Long accountId) {
        log.debug("getAccount start");
        ResponseEntity responseEntity;
        try {
            AccountDTO accountDTO = accountDataService.getAccount(accountId);
            log.info("got the account details  {}", accountDTO);
            responseEntity = ResponseEntity.ok(accountDTO);
        } catch (AccountException ae) {
            log.error("error while getting the account details  {}", ae);
            responseEntity = ResponseEntity.badRequest().body(new StatusDTO(ae.getMessage()));
        } catch (Exception e) {
            log.error("generic error while getting the account details", e);
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StatusDTO(e.getMessage()));

        }
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody AccountDTO accountDTO) {

        ResponseEntity responseEntity = null;

        if (accountDTO.getAmount() == null) {
            log.warn("invalid account details = " + accountDTO);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StatusDTO("invalid account details"));
        } else {
            accountDTO = accountDataService.save(accountDTO);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
        }
        return responseEntity;

    }

    @PutMapping
    public ResponseEntity update(@RequestBody AccountDTO accountDTO) {

        ResponseEntity responseEntity = null;

        if (accountDTO.getAccountId() == null || accountDTO.getAmount() == null) {
            log.warn("invalid account details = " + accountDTO);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StatusDTO("invalid account details"));
        } else {
            accountDTO = accountDataService.update(accountDTO);
            responseEntity = ResponseEntity.ok(accountDTO);
        }

        return responseEntity;

    }

    @DeleteMapping("{accountId}")
    public ResponseEntity removeAccount(@PathVariable("accountId") Long accountId) {
        ResponseEntity responseEntity;
        accountDataService.removeAccount(accountId);
        responseEntity = ResponseEntity.ok(HttpStatus.NO_CONTENT);
        return responseEntity;
    }
}
