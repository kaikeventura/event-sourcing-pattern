package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.repository.TransactionEventRepository
import com.kaikeventura.eventsourcingpattern.adapter.out.mysql.repository.BankAccountRepository
import com.kaikeventura.eventsourcingpattern.config.TestContainersConfig
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.DepositTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionOperation.INCREASE
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.WithdrawTransaction
import com.kaikeventura.eventsourcingpattern.factory.aBankAccount
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BankAccountUseCaseIntegrationTest : TestContainersConfig() {

    @Autowired
    private lateinit var bankAccountUseCase: BankAccountUseCase

    @Autowired
    private lateinit var transactionUseCase: TransactionUseCase

    @Autowired
    private lateinit var bankAccountRepository: BankAccountRepository

    @Autowired
    private lateinit var transactionEventRepository: TransactionEventRepository

    @AfterEach()
    fun down() {
        bankAccountRepository.deleteAll()
        transactionEventRepository.deleteAll()
    }

    @Test
    fun `should create a new bank account`() {
        bankAccountUseCase.createBankAccount(
            bankAccount = aBankAccount(
                document = "321"
            )
        )

        val bankAccount = bankAccountRepository.findByDocument("321")
        assertNotNull(bankAccount)
        assertEquals(0L, bankAccount.balance)
    }

    @Test
    fun `should validate a bank statement for bank account`() {
        val bankAccount = bankAccountUseCase.createBankAccount(
            bankAccount = aBankAccount(
                document = "321"
            )
        )

        for (index in 1..11) {
            if (index.oddNumber()) {
                transactionUseCase.handleTransaction(
                    transaction = DepositTransaction(
                        depositValue = 200_00L
                    ),
                    bankAccountId = bankAccount.id!!
                )
            } else {
                transactionUseCase.handleTransaction(
                    transaction = WithdrawTransaction(
                        withdrawValue = 200_00L
                    ),
                    bankAccountId = bankAccount.id!!
                )
            }
        }

        val bankStatement = bankAccountUseCase.getBankStatementByBankAccountId(
            bankAccountId = bankAccount.id!!,
            limit = 5
        )

        assertEquals(200_00L, bankStatement.currentBalance)
        assertEquals(5, bankStatement.statements.size)

        val statements = bankStatement.statements
        val deposits = statements.filterIsInstance<DepositTransaction>()
        val withdraws = statements.filterIsInstance<DepositTransaction>()

        deposits.forEach {
            assertEquals(INCREASE, it.operation)
            assertTrue(it.description.contains("Deposit R$"))
        }

        withdraws.forEach {
            assertEquals(INCREASE, it.operation)
            assertTrue(it.description.contains("Withdraw R$"))
        }
    }

    @Test
    fun `should rebuild the bank account balance on reference date`() {

    }

    private fun Int.oddNumber(): Boolean = this % 2 == 1
}
