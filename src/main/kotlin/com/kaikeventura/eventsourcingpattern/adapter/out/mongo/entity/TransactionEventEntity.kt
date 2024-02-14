package com.kaikeventura.eventsourcingpattern.adapter.out.mongo.entity

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.Transaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import jakarta.persistence.Column
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "transaction_event")
data class TransactionEventEntity(
    @Id
    val id: String? = null,

    val bankAccountId: UUID,

    val transaction: Transaction,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    val modifiedAt: LocalDateTime? = null
)

fun TransactionEventEntity.toModel(): TransactionEvent =
    TransactionEvent(
        id = id!!,
        bankAccountId = bankAccountId,
        transaction = transaction,
        createdAt = createdAt
    )

fun TransactionEvent.toEntity(): TransactionEventEntity =
    TransactionEventEntity(
        bankAccountId = bankAccountId,
        transaction = transaction
    )
