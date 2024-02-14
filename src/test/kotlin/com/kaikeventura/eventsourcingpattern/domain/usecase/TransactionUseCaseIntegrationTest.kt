package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.repository.TransactionEventRepository
import com.kaikeventura.eventsourcingpattern.adapter.out.mysql.repository.BankAccountRepository
import com.kaikeventura.eventsourcingpattern.config.TestContainersConfig
import com.kaikeventura.eventsourcingpattern.domain.exception.InsufficientBalanceException
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.DepositTransaction
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.WithdrawTransaction
import com.kaikeventura.eventsourcingpattern.factory.aBankAccount
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class TransactionUseCaseIntegrationTest : TestContainersConfig() {

    @Autowired
    private lateinit var transactionUseCase: TransactionUseCase

    @Autowired
    private lateinit var bankAccountUseCase: BankAccountUseCase

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
    fun `should create a deposit transaction and update bank account balance`() {
        val bankAccount = bankAccountUseCase.createBankAccount(
            bankAccount = aBankAccount(
                document = "321"
            )
        )

        assertEquals(0L, bankAccount.balance)

        transactionUseCase.handleTransaction(
            transaction = DepositTransaction(
                depositValue = 1000_00L
            ),
            bankAccountId = bankAccount.id!!
        )

        val bankAccountWithUpdatedBalance = bankAccountRepository.findByIdOrNull(bankAccount.id!!)!!
        assertEquals(1000_00L, bankAccountWithUpdatedBalance.balance)
    }

    @Test
    fun `should create a deposit transaction and withdraw transaction`() {
        val bankAccount = bankAccountUseCase.createBankAccount(
            bankAccount = aBankAccount(
                document = "321"
            )
        )

        assertEquals(0L, bankAccount.balance)

        transactionUseCase.handleTransaction(
            transaction = DepositTransaction(
                depositValue = 1000_00L
            ),
            bankAccountId = bankAccount.id!!
        )

        val bankAccountAfterDeposit = bankAccountRepository.findByIdOrNull(bankAccount.id!!)!!
        assertEquals(1000_00L, bankAccountAfterDeposit.balance)

        transactionUseCase.handleTransaction(
            transaction = WithdrawTransaction(
                withdrawValue = 600_00L
            ),
            bankAccountId = bankAccount.id!!
        )

        val bankAccountAfterWithdraw = bankAccountRepository.findByIdOrNull(bankAccount.id!!)!!
        assertEquals(400_00L, bankAccountAfterWithdraw.balance)
    }

    @Test
    fun `should not create a withdraw transaction when the balance is insufficient`() {
        val bankAccount = bankAccountUseCase.createBankAccount(
            bankAccount = aBankAccount(
                document = "321"
            )
        )

        assertEquals(0L, bankAccount.balance)

        transactionUseCase.handleTransaction(
            transaction = DepositTransaction(
                depositValue = 1000_00L
            ),
            bankAccountId = bankAccount.id!!
        )

        val bankAccountAfterDeposit = bankAccountRepository.findByIdOrNull(bankAccount.id!!)!!
        assertEquals(1000_00L, bankAccountAfterDeposit.balance)

        assertThrows(InsufficientBalanceException::class.java) {
            transactionUseCase.handleTransaction(
                transaction = WithdrawTransaction(
                    withdrawValue = 2000_00L
                ),
                bankAccountId = bankAccount.id!!
            )
        }
    }
}
