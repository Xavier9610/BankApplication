package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.devsu.hackerearth.backend.account.exception.InsufficientBalanceException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public TransactionDto getById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
        return convertToDto(transaction);
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public TransactionDto create(TransactionDto transactionDto) {
        // 1. Buscar la cuenta
        Account account = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // 2. Calcular nuevo saldo (F2)
        double newBalance = account.getInitialAmount() + transactionDto.getAmount();

        // 3. Validar saldo (F3)
        if (newBalance < 0) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }

        // 4. Actualizar saldo 
        account.setInitialAmount(newBalance);
        accountRepository.save(account);

        // 5. Crear y guardar transacción
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setType(transactionDto.getType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(newBalance);
        transaction.setAccount(account); // Relación ManyToOne

        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDto(savedTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart, Date dateTransactionEnd) {
        // F4: Buscar en BD
        List<Transaction> transactions = transactionRepository
                .findByAccount_ClientIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd);

        // Mapear a BankStatementDto
        return transactions.stream()
                .map(this::convertToBankStatementDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionDto getLastByAccountId(Long accountId) {
        Transaction transaction = transactionRepository.findTopByAccountIdOrderByDateDesc(accountId);
        if (transaction == null) {
            throw new RuntimeException("No hay transacciones para la cuenta: " + accountId);
        }
        return convertToDto(transaction);
    }

    // Mapeo
    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setBalance(transaction.getBalance());
        dto.setAccountId(transaction.getAccount().getId()); 
        return dto;
    }

    private BankStatementDto convertToBankStatementDto(Transaction transaction) {
        BankStatementDto dto = new BankStatementDto();
        
        // Mapeo 
        dto.setDate(transaction.getDate());
        dto.setTransactionType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setBalance(transaction.getBalance());
        
        // Datos de la cuenta
        Account account = transaction.getAccount();
        dto.setAccountNumber(account.getNumber());
        dto.setAccountType(account.getType());
        dto.setActive(account.isActive());
        
        // hacemos la conversión. Si tuvieras el nombre del cliente, lo pondrías aquí.
        dto.setClient(String.valueOf(account.getClientId())); 
        
        return dto;
    }
}