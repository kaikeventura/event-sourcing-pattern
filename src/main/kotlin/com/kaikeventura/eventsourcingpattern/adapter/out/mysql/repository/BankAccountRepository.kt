package com.kaikeventura.eventsourcingpattern.adapter.out.mysql.repository

import com.kaikeventura.eventsourcingpattern.adapter.out.mysql.entity.BankAccountEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankAccountRepository : JpaRepository<BankAccountEntity, UUID> {
    fun findByDocument(document: String): BankAccountEntity
}
