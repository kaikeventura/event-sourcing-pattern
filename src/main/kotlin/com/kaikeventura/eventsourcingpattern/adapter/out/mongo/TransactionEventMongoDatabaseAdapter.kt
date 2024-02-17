package com.kaikeventura.eventsourcingpattern.adapter.out.mongo

import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.entity.toEntity
import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.entity.toModel
import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.repository.TransactionEventRepository
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.port.out.database.TransactionEventDatabasePort
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Limit
import org.springframework.stereotype.Component

@Component
class TransactionEventMongoDatabaseAdapter(
    private val repository: TransactionEventRepository
) : TransactionEventDatabasePort {

    override fun save(transactionEvent: TransactionEvent): TransactionEvent =
        repository.save(transactionEvent.toEntity()).toModel()

    override fun findAllByBankAccountIdLimit(bankAccountId: UUID, limit: Int): Set<TransactionEvent> =
        repository.findByBankAccountId(
            bankAccountId = bankAccountId,
            limit = Limit.of(limit)
        ).map { it.toModel() }.toSet()

    override fun findAllByBankAccountIdLimitDate(bankAccountId: UUID, limitDate: LocalDateTime): Set<TransactionEvent> =
        repository.findByBankAccountIdAndCreatedAtBefore(
            bankAccountId = bankAccountId,
            createdAt = limitDate
        ).map { it.toModel() }.sortedBy { it.occurredAt }.toSet()
}
