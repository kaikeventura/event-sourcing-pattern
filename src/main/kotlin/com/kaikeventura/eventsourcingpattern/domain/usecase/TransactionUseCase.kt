package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.DepositTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.NewAccountTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.Transaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.WithdrawTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.service.TransactionEventService
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class TransactionUseCase(
    private val transactionEventService: TransactionEventService
) {

    fun handleTransaction(
        transaction: Transaction,
        bankAccountId: UUID
    ) {
        when (transaction) {
            is NewAccountTransaction -> handleNewAccountTransaction(transaction, bankAccountId)
            is DepositTransaction -> handleDepositTransaction(transaction, bankAccountId)
            is WithdrawTransaction -> handleWithdrawTransaction(transaction, bankAccountId)
        }
    }

    private fun handleNewAccountTransaction(
        transaction: NewAccountTransaction,
        bankAccountId: UUID
    ) {
        val savedTransactionEvent = transactionEventService.save(
            transactionEvent = TransactionEvent(
                bankAccountId = bankAccountId,
                transaction = transaction
            )
        )
    }

    private fun handleDepositTransaction(
        transaction: DepositTransaction,
        bankAccountId: UUID
    ) {

    }

    private fun handleWithdrawTransaction(
        transaction: WithdrawTransaction,
        bankAccountId: UUID
    ) {

    }

    private fun sendBalanceEvent() {

    }
}
