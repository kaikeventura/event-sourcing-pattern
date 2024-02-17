package com.kaikeventura.eventsourcingpattern.domain.model.transaction

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.DECREASE
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.INCREASE
import java.time.LocalDateTime
import java.time.LocalDateTime.now

interface Transaction {
    val totalValue: Long
    val operation: TransactionOperation
    val description: String
    val occurredAt: LocalDateTime
}

data class DepositTransaction(
    override val totalValue: Long,
    override val operation: TransactionOperation,
    override val description: String,
    override val occurredAt: LocalDateTime
) : Transaction {
    constructor(
        depositValue: Long,
        occurredAt: LocalDateTime = now()
    ): this(
        totalValue = depositValue,
        operation = INCREASE,
        description = "Deposit R$ $depositValue",
        occurredAt = occurredAt
    )
}

data class WithdrawTransaction(
    override val totalValue: Long,
    override val operation: TransactionOperation,
    override val description: String,
    override val occurredAt: LocalDateTime
) : Transaction {
    constructor(
        withdrawValue: Long,
        occurredAt: LocalDateTime = now()
    ): this(
        totalValue = withdrawValue,
        operation = DECREASE,
        description = "Withdraw R$ $withdrawValue",
        occurredAt = occurredAt
    )
}

enum class TransactionOperation {

    INCREASE {
        override fun calculate(currentValue: Long, operationValue: Long): Long = currentValue.plus(operationValue)
    },
    DECREASE {
        override fun calculate(currentValue: Long, operationValue: Long): Long = currentValue.minus(operationValue)
    };

    abstract fun calculate(currentValue: Long, operationValue: Long): Long
}
