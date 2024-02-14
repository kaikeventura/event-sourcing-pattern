package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.DepositTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.Transaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.WithdrawTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.service.TransactionEventService
import java.util.UUID
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionUseCase(
    private val transactionEventService: TransactionEventService,
    private val bankAccountUseCase: BankAccountUseCase
) {

    @Transactional(rollbackFor = [Exception::class])
    fun handleTransaction(
        transaction: Transaction,
        bankAccountId: UUID
    ) {
        when (transaction) {
            is DepositTransaction -> handleDepositTransaction(transaction, bankAccountId)
            is WithdrawTransaction -> handleWithdrawTransaction(transaction, bankAccountId)
        }
    }

    private fun handleDepositTransaction(
        transaction: DepositTransaction,
        bankAccountId: UUID
    ) {
        val transactionEvent = transactionEventService.save(
            transactionEvent = TransactionEvent(
                bankAccountId = bankAccountId,
                transaction = transaction
            )
        )
        bankAccountUseCase.updateBalance(transactionEvent)
    }

    private fun handleWithdrawTransaction(
        transaction: WithdrawTransaction,
        bankAccountId: UUID
    ) {
        val transactionEvent = transactionEventService.save(
            transactionEvent = TransactionEvent(
                bankAccountId = bankAccountId,
                transaction = transaction
            )
        )
        bankAccountUseCase.updateBalance(transactionEvent)
    }
}
