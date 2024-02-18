package com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto

import com.kaikeventura.eventsourcingpattern.domain.account.model.BankStatement
import com.kaikeventura.eventsourcingpattern.domain.account.model.Statement
import java.time.LocalDateTime
import java.util.UUID

data class BankStatementResponseDTO(
    val bankAccountId: UUID,
    val currentBalance: Long,
    val bankStatementDate: LocalDateTime,
    val statements: Set<StatementResponseDTO>
)

data class StatementResponseDTO(
    val id: String,
    val value: Long,
    val description: String,
    val occurredAt: LocalDateTime
)

fun BankStatement.toDTO(): BankStatementResponseDTO =
    BankStatementResponseDTO(
        bankAccountId = bankAccountId,
        currentBalance = currentBalance,
        bankStatementDate = bankStatementDate,
        statements = statements.map { it.toDTO() }.sortedBy { it.occurredAt }.toSet()
    )

private fun Statement.toDTO(): StatementResponseDTO =
    StatementResponseDTO(
        id = id,
        value = value,
        description = description,
        occurredAt = occurredAt
    )
