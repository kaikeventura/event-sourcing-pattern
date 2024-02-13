package com.kaikeventura.eventsourcingpattern.domain.model.transaction

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.DECREASE
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.INCREASE

interface Transaction {
    val value: Long
    val operation: TransactionOperation
}

data class NewAccountTransaction(
    val purchaseValue: Long = 0
) : Transaction {
    override val value: Long = purchaseValue
    override val operation: TransactionOperation = INCREASE
}

data class DepositTransaction(
    val purchaseValue: Long
) : Transaction {
    override val value: Long = purchaseValue
    override val operation: TransactionOperation = INCREASE
}

data class WithdrawTransaction(
    val reimbursementValue: Long
) : Transaction {
    override val value: Long = reimbursementValue
    override val operation: TransactionOperation = DECREASE
}

enum class TransactionOperation {
    INCREASE, DECREASE
}
