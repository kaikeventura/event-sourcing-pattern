package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.domain.model.account.BankAccount
import com.kaikeventura.eventsourcingpattern.domain.model.account.BankStatement
import com.kaikeventura.eventsourcingpattern.domain.model.account.toStatement
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.service.BankAccountService
import com.kaikeventura.eventsourcingpattern.domain.service.TransactionEventService
import java.util.UUID
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BankAccountUseCase(
    private val bankAccountService: BankAccountService,
    private val transactionEventService: TransactionEventService
) {

    private val logger = getLogger(this::class.java)

    @Transactional(rollbackFor = [Exception::class])
    fun createBankAccount(bankAccount: BankAccount) = run {
        logger.info("Stating creating a new bank account")
        bankAccountService.saveBankAccount(
            bankAccount = bankAccount
        )
    }.also { logger.info("Finished creating a new bank account") }

    fun getBankStatementByBankAccountId(bankAccountId: UUID, limit: Int = 10): BankStatement {
        val bankAccount = getBankAccountById(bankAccountId)
        val transactionEvents = transactionEventService.findAllEventsByBankAccountIdLimit(bankAccountId, limit)

        return BankStatement(
            bankAccountId = bankAccount.id!!,
            currentBalance = bankAccount.balance,
            statements = transactionEvents.map { it.toStatement() }.toSet()
        )
    }

    internal fun updateBalance(event: TransactionEvent) {
        val bankAccount = getBankAccountById(event.bankAccountId)
        bankAccountService.saveBankAccount(bankAccount.withNewBalance(event))
    }

    fun getCurrentBalanceByBankAccountId(bankAccountId: UUID): Long =
        bankAccountService.findCurrentBalanceByBankAccountId(bankAccountId)
            ?: throw RuntimeException("Bank account $bankAccountId not found")

    private fun getBankAccountById(bankAccountId: UUID): BankAccount =
        bankAccountService.findBankAccountById(bankAccountId)
            ?: throw RuntimeException("Bank account $bankAccountId not found")
}
