package com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto

import com.kaikeventura.eventsourcingpattern.domain.transaction.model.DepositTransaction
import com.kaikeventura.eventsourcingpattern.domain.transaction.model.Transaction
import com.kaikeventura.eventsourcingpattern.domain.transaction.model.WithdrawTransaction
import java.time.LocalDateTime

data class DepositRequestDTO(
    val totalValue: Long,
    val occurredAt: LocalDateTime
) {
    fun toModel(): Transaction =
        DepositTransaction(totalValue, occurredAt)
}

data class WithdrawRequestDTO(
    val totalValue: Long,
    val occurredAt: LocalDateTime
) {
    fun toModel(): Transaction =
        WithdrawTransaction(totalValue, occurredAt)
}

data class TransactionResponseDTO(
    val transactionId: String
)

fun String.toDTO(): TransactionResponseDTO =
    TransactionResponseDTO(
        transactionId = this
    )
