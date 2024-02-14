package com.kaikeventura.eventsourcingpattern.domain.model.transaction

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.DECREASE
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.INCREASE

interface Transaction {
    val totalValue: Long
    val operation: TransactionOperation
}

data class NewAccountTransaction(
    val initialBalance: Long = 0,
    override val totalValue: Long = initialBalance,
    override val operation: TransactionOperation = INCREASE
) : Transaction

data class DepositTransaction(
    val depositValue: Long,
    override val totalValue: Long = depositValue,
    override val operation: TransactionOperation = INCREASE
) : Transaction

data class WithdrawTransaction(
    val withdrawValue: Long,
    override val totalValue: Long =  withdrawValue,
    override val operation: TransactionOperation = DECREASE
) : Transaction

enum class TransactionOperation {
    INCREASE, DECREASE
}
