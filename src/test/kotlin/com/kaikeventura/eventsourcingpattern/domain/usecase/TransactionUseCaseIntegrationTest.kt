package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.repository.TransactionEventRepository
import com.kaikeventura.eventsourcingpattern.adapter.out.mysql.repository.BankAccountRepository
import com.kaikeventura.eventsourcingpattern.config.TestContainersConfig
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.DepositTransaction
import com.kaikeventura.eventsourcingpattern.factory.aBankAccount
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
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
}
