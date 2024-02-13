package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.DepositTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.NewAccountTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.Transaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.WithdrawTransaction
import org.springframework.stereotype.Component

@Component
class TransactionUseCase {

    fun handleTransaction(transaction: Transaction) {
        when (transaction) {
            is NewAccountTransaction -> handleNewAccountTransaction(transaction)
            is DepositTransaction -> handleDepositTransaction(transaction)
            is WithdrawTransaction -> handleWithdrawTransaction(transaction)
        }
    }

    private fun handleNewAccountTransaction(transaction: NewAccountTransaction) {

    }

    private fun handleDepositTransaction(transaction: DepositTransaction) {

    }

    private fun handleWithdrawTransaction(transaction: WithdrawTransaction) {

    }

    private fun sendBalanceEvent() {

    }
}
