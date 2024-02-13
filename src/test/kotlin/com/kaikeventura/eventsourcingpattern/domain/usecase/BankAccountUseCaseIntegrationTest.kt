package com.kaikeventura.eventsourcingpattern.domain.usecase

import com.kaikeventura.eventsourcingpattern.factory.aBankAccount
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BankAccountUseCaseIntegrationTest {

    @Autowired
    private lateinit var useCase: BankAccountUseCase

    @Test
    fun `should be create a new bank account`() {
        useCase.createBankAccount(
            bankAccount = aBankAccount()
        )
    }
}
