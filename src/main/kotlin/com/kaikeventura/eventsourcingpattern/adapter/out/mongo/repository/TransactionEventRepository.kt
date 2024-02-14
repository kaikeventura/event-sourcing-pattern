package com.kaikeventura.eventsourcingpattern.adapter.out.mongo.repository

import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.entity.TransactionEventEntity
import java.util.UUID
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionEventRepository : MongoRepository<TransactionEventEntity, UUID> {
    fun findByBankAccountId(bankAccountId: UUID): List<TransactionEventEntity>
}
