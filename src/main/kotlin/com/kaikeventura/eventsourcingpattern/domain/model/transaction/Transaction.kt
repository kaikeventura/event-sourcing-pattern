package com.kaikeventura.eventsourcingpattern.domain.model.transaction

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.DECREASE
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.INCREASE

interface Transaction {
    val value: Long
    val operation: TransactionOperation
}

data class NewAccountTransaction(
    val initialBalance: Long = 0
) : Transaction {
    override val value: Long = initialBalance
    override val operation: TransactionOperation = INCREASE
}

data class DepositTransaction(
    val depositValue: Long
) : Transaction {
    override val value: Long = depositValue
    override val operation: TransactionOperation = INCREASE
}

data class WithdrawTransaction(
    val withdrawValue: Long
) : Transaction {
    override val value: Long = withdrawValue
    override val operation: TransactionOperation = DECREASE
}

enum class TransactionOperation {
    INCREASE, DECREASE
}
