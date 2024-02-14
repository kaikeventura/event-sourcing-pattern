package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.domain.model.account.BankAccount
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.service.BankAccountService
import java.util.UUID
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BankAccountUseCase(
    private val bankAccountService: BankAccountService
) {

    private val logger = getLogger(this::class.java)

    @Transactional(rollbackFor = [Exception::class])
    fun createBankAccount(bankAccount: BankAccount) = run {
        logger.info("Stating creating a new bank account")
        bankAccountService.saveBankAccount(
            bankAccount = bankAccount
        )
    }.also { logger.info("Finished creating a new bank account") }

    fun updateBalance(event: TransactionEvent) {
        val bankAccount = bankAccountService.findBankAccountById(event.bankAccountId)
            ?: throw RuntimeException("Bank account ${event.bankAccountId} not found")

        bankAccountService.saveBankAccount(bankAccount.withNewBalance(event))
    }

    fun getCurrentBalanceByBankAccountId(bankAccountId: UUID): Long =
        bankAccountService.findCurrentBalanceByBankAccountId(bankAccountId)
            ?: throw RuntimeException("Bank account $bankAccountId not found")
}
