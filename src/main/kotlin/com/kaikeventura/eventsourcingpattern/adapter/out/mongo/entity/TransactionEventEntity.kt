package com.kaikeventura.eventsourcingpattern.adapter.out.mongo.entity

import com.fasterxml.jackson.databind.ObjectMapper
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.Transaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import jakarta.persistence.Column
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.mongodb.core.mapping.Document

@Document(collation = "transaction_event")
data class TransactionEventEntity(
    @Id
    val id: String? = null,

    val bankAccountId: UUID,

    val transactionPayload: String,

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    val modifiedAt: LocalDateTime? = null
)

fun TransactionEventEntity.toModel(): TransactionEvent =
    TransactionEvent(
        id = id!!,
        bankAccountId = bankAccountId,
//        transaction = Gson().fromJson(transactionPayload, Transaction::class.java)
        transaction = ObjectMapper().readValue(transactionPayload, Transaction::class.java)
    )

fun TransactionEvent.toEntity(): TransactionEventEntity =
    TransactionEventEntity(
        bankAccountId = bankAccountId,
//        transactionPayload = Gson().toJson(transaction)
        transactionPayload = ObjectMapper().writeValueAsString(transaction)
    )