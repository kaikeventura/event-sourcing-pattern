package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.domain.model.account.BankAccount
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.NewAccountTransaction
import com.kaikeventura.eventsourcingpattern.domain.service.BankAccountService
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BankAccountUseCase(
    private val bankAccountService: BankAccountService,
    private val transactionUseCase: TransactionUseCase
) {

    private val logger = getLogger(this::class.java)

    @Transactional(rollbackFor = [Exception::class])
    fun createBankAccount(bankAccount: BankAccount, initialBalance: Long = 0) {
        logger.info("Stating creating a new bank account")
        bankAccountService.saveBankAccount(
            bankAccount = bankAccount
        )

        transactionUseCase.handleTransaction(
            transaction = NewAccountTransaction(
                initialBalance = initialBalance
            )
        )

        logger.info("Finished creating a new bank account")
    }
}
