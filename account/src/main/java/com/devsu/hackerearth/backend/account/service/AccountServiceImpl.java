package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public AccountDto getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        return convertToDto(account);
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public AccountDto create(AccountDto accountDto) {
        Account account = convertToEntity(accountDto);
        Account savedAccount = accountRepository.save(account);
        return convertToDto(savedAccount);
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public AccountDto update(AccountDto accountDto) {
        Account account = accountRepository.findById(accountDto.getId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        return convertToDto(accountRepository.save(account));
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // Actualizamos solo el campo 
        account.setActive(partialAccountDto.isActive());

        return convertToDto(accountRepository.save(account));
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public void deleteById(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrada");
        }
        accountRepository.deleteById(id);
    }

    // --- Mapeadores ---
    private AccountDto convertToDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setNumber(account.getNumber());
        dto.setType(account.getType());
        dto.setInitialAmount(account.getInitialAmount());
        dto.setActive(account.isActive());
        dto.setClientId(account.getClientId());
        return dto;
    }

    private Account convertToEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setNumber(dto.getNumber());
        account.setType(dto.getType());
        account.setInitialAmount(dto.getInitialAmount());
        account.setActive(dto.isActive());
        account.setClientId(dto.getClientId());
        return account;
    }
}