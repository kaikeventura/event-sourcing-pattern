package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.repository.TransactionEventRepository
import com.kaikeventura.eventsourcingpattern.adapter.out.mysql.repository.BankAccountRepository
import com.kaikeventura.eventsourcingpattern.config.TestContainersConfig
import com.kaikeventura.eventsourcingpattern.factory.aBankAccount
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BankAccountUseCaseIntegrationTest : TestContainersConfig() {

    @Autowired
    private lateinit var useCase: BankAccountUseCase

    @Autowired
    private lateinit var bankAccountRepository: BankAccountRepository

    @Autowired
    private lateinit var transactionEventRepository: TransactionEventRepository

    @AfterEach()
    fun down() {
        bankAccountRepository.deleteAll()
    }

    @Test
    fun `should be create a new bank account with initial balance`() {
        useCase.createBankAccount(
            bankAccount = aBankAccount(
                document = "321"
            ),
            initialBalance = 100_00L
        )

        val bankAccount = bankAccountRepository.findByDocument("321")
        assertNotNull(bankAccount)

        transactionEventRepository.findByBankAccountId(bankAccount.id!!)
    }
}
