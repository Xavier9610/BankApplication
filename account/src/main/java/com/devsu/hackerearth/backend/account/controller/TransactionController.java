package com.devsu.hackerearth.backend.account.controller;

import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @SuppressWarnings("null")
    public ResponseEntity<List<TransactionDto>> getAll() {
        // GET /api/transactions
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/{id}")
    @SuppressWarnings("null")
    public ResponseEntity<TransactionDto> get(@PathVariable Long id) {
        // GET /api/transactions/{id}
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transactionDto) {
        // POST /api/transactions
        TransactionDto createdTransaction = transactionService.create(transactionDto);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/clients/{clientId}/report")
    @SuppressWarnings("null")
    public ResponseEntity<List<BankStatementDto>> report(
            @PathVariable Long clientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {
        // GET /api/transactions/clients/{clientId}/report?dateTransactionStart=yyyy-MM-dd&dateTransactionEnd=yyyy-MM-dd
        List<BankStatementDto> report = transactionService.getAllByAccountClientIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd);
        return ResponseEntity.ok(report);
    }
}