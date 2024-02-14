package com.kaikeventura.eventsourcingpattern.domain.model.account

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
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
