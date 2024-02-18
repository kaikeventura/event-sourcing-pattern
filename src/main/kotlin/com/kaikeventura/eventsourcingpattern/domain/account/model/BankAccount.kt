package com.kaikeventura.eventsourcingpattern.domain.account.model

import com.kaikeventura.eventsourcingpattern.domain.transaction.model.TransactionEvent
import java.time.LocalDate
import java.util.UUID

data class BankAccount(
    val id: UUID? = null,
    val balance: Long = 0L,
    val name: String,
    val document: String,
    val birthDate: LocalDate
) {
    fun withNewBalance(event: TransactionEvent): BankAccount =
        copy(
            balance = event.transaction.operation.calculate(
                currentValue = balance,
                operationValue = event.transaction.totalValue
            )
        )
}
