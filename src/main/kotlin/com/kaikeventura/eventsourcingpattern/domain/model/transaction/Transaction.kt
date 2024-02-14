package com.kaikeventura.eventsourcingpattern.domain.model.transaction

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.DECREASE
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.INCREASE

interface Transaction {
    val totalValue: Long
    val operation: TransactionOperation
}

data class DepositTransaction(
    val depositValue: Long,
    override val totalValue: Long = depositValue,
    override val operation: TransactionOperation = INCREASE
) : Transaction {
    constructor(depositValue: Long): this(
        depositValue = depositValue,
        totalValue = depositValue,
        operation = INCREASE
    )
}

data class WithdrawTransaction(
    val withdrawValue: Long,
    override val totalValue: Long =  withdrawValue,
    override val operation: TransactionOperation = DECREASE
) : Transaction

enum class TransactionOperation {

    INCREASE {
        override fun calculate(currentValue: Long, operationValue: Long): Long = currentValue.plus(operationValue)
    },
    DECREASE {
        override fun calculate(currentValue: Long, operationValue: Long): Long = currentValue.minus(operationValue)
    };

    abstract fun calculate(currentValue: Long, operationValue: Long): Long
}
