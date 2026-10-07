package com.devsu.hackerearth.backend.account.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.devsu.hackerearth.backend.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    // Método personalizado para buscar todas las cuentas de un cliente específico
    List<Account> findByClientId(Long clientId);
    
}