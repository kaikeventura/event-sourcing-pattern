package com.kaikeventura.eventsourcingpattern.domain.account.model

import com.kaikeventura.eventsourcingpattern.domain.transaction.model.TransactionEvent
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.UUID

data class BankStatement(
    val bankAccountId: UUID,
    val currentBalance: Long,
    val bankStatementDate: LocalDateTime = now(),
    val statements: Set<Statement>
)

data class Statement(
    val id: String,
    val value: Long,
    val description: String,
    val occurredAt: LocalDateTime
)

fun TransactionEvent.toStatement(): Statement =
    Statement(
        id = id!!,
        value = transaction.totalValue,
        description = transaction.description,
        occurredAt = occurredAt
    )
