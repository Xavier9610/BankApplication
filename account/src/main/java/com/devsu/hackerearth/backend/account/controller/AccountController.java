package com.devsu.hackerearth.backend.account.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @SuppressWarnings("null")
    public ResponseEntity<List<AccountDto>> getAll() {
        // GET /api/accounts
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/{id}")
    @SuppressWarnings("null")
    public ResponseEntity<AccountDto> get(@PathVariable Long id) {
        // GET /api/accounts/{id}
        return ResponseEntity.ok(accountService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
        // POST /api/accounts
        AccountDto createdAccount = accountService.create(accountDto);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @SuppressWarnings("null")
    public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        // PUT /api/accounts/{id}
        // Aseguramos que el ID del DTO sea el mismo que el de la URL
        accountDto.setId(id);
        return ResponseEntity.ok(accountService.update(accountDto));
    }

    @PatchMapping("/{id}")
    @SuppressWarnings("null")
    public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id, @RequestBody PartialAccountDto partialAccountDto) {
        // PATCH /api/accounts/{id}
        return ResponseEntity.ok(accountService.partialUpdate(id, partialAccountDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // DELETE /api/accounts/{id}
        accountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}