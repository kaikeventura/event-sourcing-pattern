package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.domain.exception.InsufficientBalanceException
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.DepositTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.Transaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.WithdrawTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.service.TransactionEventService
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionUseCase(
    private val transactionEventService: TransactionEventService,
    private val bankAccountUseCase: BankAccountUseCase
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(rollbackFor = [Exception::class])
    fun handleTransaction(
        transaction: Transaction,
        bankAccountId: UUID
    ) {
        logger.info("Starting handle transaction $transaction")

        when (transaction) {
            is DepositTransaction -> handleDepositTransaction(transaction, bankAccountId)
            is WithdrawTransaction -> handleWithdrawTransaction(transaction, bankAccountId)
        }

        logger.info("Finishing handle transaction $transaction")
    }

    private fun handleDepositTransaction(
        transaction: DepositTransaction,
        bankAccountId: UUID
    ) {
        logger.info("Starting execution deposit for bank account $bankAccountId")
        val transactionEvent = transactionEventService.save(
            transactionEvent = TransactionEvent(
                bankAccountId = bankAccountId,
                transaction = transaction
            )
        )
        bankAccountUseCase.updateBalance(transactionEvent)
        logger.info("Finishing execution deposit for bank account $bankAccountId")
    }

    private fun handleWithdrawTransaction(
        transaction: WithdrawTransaction,
        bankAccountId: UUID
    ) {
        logger.info("Starting execution deposit for bank account $bankAccountId")
        val transactionEvent = transactionEventService.save(
            transactionEvent = TransactionEvent(
                bankAccountId = bankAccountId,
                transaction = transaction
            )
        )

        withdrawValidator(bankAccountId, transaction)
        bankAccountUseCase.updateBalance(transactionEvent)

        logger.info("Finishing execution deposit for bank account $bankAccountId")
    }

    private fun withdrawValidator(
        bankAccountId: UUID,
        transaction: WithdrawTransaction
    ) {
        val currentBalance = bankAccountUseCase.getCurrentBalanceByBankAccountId(bankAccountId)
        if (transaction.totalValue > currentBalance) {
            logger.error(
                "Insufficient balance value $currentBalance to withdraw value ${transaction.totalValue} for bank account $bankAccountId"
            )
            throw InsufficientBalanceException(bankAccountId)
        }
    }
}
