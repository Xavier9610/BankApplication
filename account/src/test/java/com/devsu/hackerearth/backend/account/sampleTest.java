package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.controller.AccountController;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.devsu.hackerearth.backend.account.exception.InsufficientBalanceException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.service.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class sampleTest {

	private AccountService accountService = mock(AccountService.class);
	private AccountController accountController = new AccountController(accountService);

	@Test
	void createAccountTest() {
		// Arrange
		AccountDto newAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		AccountDto createdAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		when(accountService.create(newAccount)).thenReturn(createdAccount);

		// Act
		ResponseEntity<AccountDto> response = accountController.create(newAccount);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdAccount, response.getBody());
	}
}

// ==========================================
// F5: PRUEBA UNITARIA (Lógica de negocio F3)
// ==========================================
@ExtendWith(MockitoExtension.class)
class AccountUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void testCreateTransactionInsufficientBalance() {
        // Arrange: Cuenta con saldo 100
        Account account = new Account();
        account.setId(1L);
        account.setInitialAmount(100.0);

        TransactionDto dto = new TransactionDto();
        dto.setAccountId(1L);
        dto.setAmount(-200.0); // Retiro de 200, debería fallar

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Act & Assert: Esperamos que lance la excepción
        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.create(dto);
        });
    }
}

// ==========================================
// F6: PRUEBA DE INTEGRACIÓN (Endpoint HTTP)
// ==========================================
@SpringBootTest
@AutoConfigureMockMvc
class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SuppressWarnings("null")
    void testCreateAccountIntegration() throws Exception {
        // Arrange
        Account account = new Account();
        account.setNumber("ACC-001");
        account.setType("Ahorros");
        account.setInitialAmount(500.0);
        account.setActive(true);
        account.setClientId(1L);

        String jsonPayload = objectMapper.writeValueAsString(account);

        // Act & Assert
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value("ACC-001"))
                .andExpect(jsonPath("$.type").value("Ahorros"))
                .andExpect(jsonPath("$.initialAmount").value(500.0));
    }
}