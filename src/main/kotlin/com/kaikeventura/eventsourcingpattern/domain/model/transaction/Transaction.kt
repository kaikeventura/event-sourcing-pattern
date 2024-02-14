package com.kaikeventura.eventsourcingpattern.domain.model.transaction

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.DECREASE
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.INCREASE

interface Transaction {
    val totalValue: Long
    val operation: TransactionOperation
    val description: String
}

data class DepositTransaction(
    override val totalValue: Long,
    override val operation: TransactionOperation,
    override val description: String
) : Transaction {
    constructor(depositValue: Long): this(
        totalValue = depositValue,
        operation = INCREASE,
        description = "Deposit R$ $depositValue"
    )
}

data class WithdrawTransaction(
    override val totalValue: Long,
    override val operation: TransactionOperation,
    override val description: String
) : Transaction {
    constructor(withdrawValue: Long): this(
        totalValue = withdrawValue,
        operation = DECREASE,
        description = "Withdraw R$ $withdrawValue"
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
