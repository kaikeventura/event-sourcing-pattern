package com.kaikeventura.eventsourcingpattern.adapter.out.mongo.repository

import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.entity.TransactionEventEntity
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Limit
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionEventRepository : MongoRepository<TransactionEventEntity, String> {
    fun findByBankAccountId(bankAccountId: UUID, limit: Limit): List<TransactionEventEntity>
    fun findByBankAccountIdAndCreatedAtBefore(bankAccountId: UUID, createdAt: LocalDateTime): List<TransactionEventEntity>
}
