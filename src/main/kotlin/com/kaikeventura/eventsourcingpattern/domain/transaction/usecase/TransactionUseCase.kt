package com.kaikeventura.eventsourcingpattern.domain.transaction.usecase

import com.kaikeventura.eventsourcingpattern.domain.common.exception.InsufficientBalanceException
import com.kaikeventura.eventsourcingpattern.domain.transaction.model.DepositTransaction
import com.kaikeventura.eventsourcingpattern.domain.transaction.model.Transaction
import com.kaikeventura.eventsourcingpattern.domain.transaction.model.WithdrawTransaction
import com.kaikeventura.eventsourcingpattern.domain.transaction.model.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.transaction.service.TransactionEventService
import com.kaikeventura.eventsourcingpattern.domain.account.usecase.BankAccountUseCase
import com.kaikeventura.eventsourcingpattern.domain.transaction.port.`in`.TransactionPort
import java.io.InvalidClassException
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionUseCase(
    private val transactionEventService: TransactionEventService,
    private val bankAccountUseCase: BankAccountUseCase
): TransactionPort {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(rollbackFor = [Exception::class])
    override fun createTransaction(
        bankAccountId: UUID,
        transaction: Transaction
    ): String {
        logger.info("Starting handle transaction $transaction")

        return when (transaction) {
            is DepositTransaction -> handleDepositTransaction(transaction, bankAccountId)
            is WithdrawTransaction -> handleWithdrawTransaction(transaction, bankAccountId)
            else -> throw InvalidClassException("There is not implementation for this transaction $transaction")
        }.also {
            logger.info("Finishing handle transaction $transaction")
        }
    }

    private fun handleDepositTransaction(
        transaction: DepositTransaction,
        bankAccountId: UUID
    ): String {
        logger.info("Starting execution deposit for bank account $bankAccountId")
        val transactionEvent = transactionEventService.save(
            transactionEvent = TransactionEvent(
                bankAccountId = bankAccountId,
                transaction = transaction,
                occurredAt = transaction.occurredAt
            )
        )
        bankAccountUseCase.updateBalance(transactionEvent)
        logger.info("Finishing execution deposit for bank account $bankAccountId")

        return transactionEvent.id!!
    }

    private fun handleWithdrawTransaction(
        transaction: WithdrawTransaction,
        bankAccountId: UUID
    ): String {
        logger.info("Starting execution deposit for bank account $bankAccountId")
        val transactionEvent = transactionEventService.save(
            transactionEvent = TransactionEvent(
                bankAccountId = bankAccountId,
                transaction = transaction,
                occurredAt = transaction.occurredAt
            )
        )

        withdrawValidator(bankAccountId, transaction)
        bankAccountUseCase.updateBalance(transactionEvent)

        logger.info("Finishing execution deposit for bank account $bankAccountId")

        return transactionEvent.id!!
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
