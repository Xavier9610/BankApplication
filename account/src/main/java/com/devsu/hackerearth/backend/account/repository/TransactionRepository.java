package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.devsu.hackerearth.backend.account.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /*
     * F4: Reporte de Estado de Cuenta.
     * Busca todas las transacciones asociadas a un cliente en un rango de fechas.
     */
    List<Transaction> findByAccount_ClientIdAndDateBetween(Long clientId, Date dateStart, Date dateEnd);

    /**
     * Obtiene el último movimiento de una cuenta específica.
     */
    Transaction findTopByAccountIdOrderByDateDesc(Long accountId);
    
}