package com.kaikeventura.eventsourcingpattern.adapter.out.mysql.repository

import com.kaikeventura.eventsourcingpattern.adapter.out.mysql.entity.BankAccountEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BankAccountRepository : JpaRepository<BankAccountEntity, UUID> {
    fun findByDocument(document: String): BankAccountEntity

    @Query("""
        select 
            balance 
        from bank_account 
        where id = :id
    """)
    fun balanceById(id: UUID): Long?
}
