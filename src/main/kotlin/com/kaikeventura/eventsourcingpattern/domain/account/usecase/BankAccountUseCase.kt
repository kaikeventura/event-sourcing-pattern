package com.kaikeventura.eventsourcingpattern.domain.account.usecase

import com.kaikeventura.eventsourcingpattern.domain.common.exception.BankAccountNotFoundException
import com.kaikeventura.eventsourcingpattern.domain.account.model.BankAccount
import com.kaikeventura.eventsourcingpattern.domain.account.model.BankStatement
import com.kaikeventura.eventsourcingpattern.domain.account.model.toStatement
import com.kaikeventura.eventsourcingpattern.domain.account.port.`in`.BankAccountPort
import com.kaikeventura.eventsourcingpattern.domain.transaction.model.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.account.service.BankAccountService
import com.kaikeventura.eventsourcingpattern.domain.transaction.service.TransactionEventService
import java.time.LocalDateTime
import java.util.UUID
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BankAccountUseCase(
    private val bankAccountService: BankAccountService,
    private val transactionEventService: TransactionEventService
) : BankAccountPort {

    private val logger = getLogger(this::class.java)

    @Transactional(rollbackFor = [Exception::class])
    override fun createBankAccount(bankAccount: BankAccount) = run {
        logger.info("Stating creating a new bank account")
        bankAccountService.saveBankAccount(
            bankAccount = bankAccount
        )
    }.also { logger.info("Finished creating a new bank account") }

    override fun getBankStatementByBankAccountId(bankAccountId: UUID, limit: Int): BankStatement {
        val bankAccount = getBankAccountById(bankAccountId)
        val transactionEvents = transactionEventService.findAllEventsByBankAccountIdLimit(bankAccountId, limit)

        return BankStatement(
            bankAccountId = bankAccount.id!!,
            currentBalance = bankAccount.balance,
            statements = transactionEvents.map { it.toStatement() }.toSet()
        )
    }

    override fun rebuildBankAccountBalanceUntil(bankAccountId: UUID, referenceDate: LocalDateTime): Pair<LocalDateTime, Long> {
        verifyIfBankAccountExists(bankAccountId)
        val events = transactionEventService.findAllEventsByBankAccountIdLimitDate(
            bankAccountId = bankAccountId,
            limitDate = referenceDate
        )

        val balance = events.fold(0L) { acc, transactionEvent ->
            val transaction = transactionEvent.transaction
            transaction.operation.calculate(acc, transaction.totalValue)
        }

        return Pair(referenceDate, balance)
    }

    internal fun updateBalance(event: TransactionEvent) {
        val bankAccount = getBankAccountById(event.bankAccountId)
        bankAccountService.saveBankAccount(bankAccount.withNewBalance(event))
    }

    fun getCurrentBalanceByBankAccountId(bankAccountId: UUID): Long =
        bankAccountService.findCurrentBalanceByBankAccountId(bankAccountId)
            ?: throw BankAccountNotFoundException(bankAccountId)

    private fun getBankAccountById(bankAccountId: UUID): BankAccount =
        bankAccountService.findBankAccountById(bankAccountId)
            ?: throw BankAccountNotFoundException(bankAccountId)

    private fun verifyIfBankAccountExists(bankAccountId: UUID) {
        if (!bankAccountService.existsByAccountById(bankAccountId)) {
            throw BankAccountNotFoundException(bankAccountId)
        }
    }
}
