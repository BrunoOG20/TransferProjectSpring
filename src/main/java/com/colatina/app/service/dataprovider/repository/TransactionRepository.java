package com.colatina.app.service.dataprovider.repository;

import com.colatina.app.service.dataprovider.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    List<TransactionEntity> findAllByAccountOriginIdAndCreatedAtBetween(Integer accountId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT transaction FROM TransactionEntity transaction WHERE transaction.accountOrigin.id = :accountId " +
            "AND transaction.status LIKE 'PROCESSED' ")
    List<TransactionEntity> findAllByAccountTransitionProcessed(@Param("accountId") Integer accountId);

    @Query("SELECT transaction FROM TransactionEntity transaction WHERE transaction.accountOrigin.id = :accountId " +
            "AND transaction.status LIKE 'REFUSED' ")
    List<TransactionEntity> findAllByAccountTransitionRefused(@Param("accountId") Integer accountId);

    @Query("SELECT transaction FROM TransactionEntity transaction WHERE transaction.accountDestination.id = :accountId")
    List<TransactionEntity> findAllByAccountReceiveTransition(@Param("accountId") Integer accountId);
}
