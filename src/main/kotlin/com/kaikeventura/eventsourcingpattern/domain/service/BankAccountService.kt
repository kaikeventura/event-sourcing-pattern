package com.kaikeventura.eventsourcingpattern.domain.service

import com.kaikeventura.eventsourcingpattern.domain.model.account.BankAccount
import com.kaikeventura.eventsourcingpattern.domain.port.out.database.BankAccountDatabasePort
import org.springframework.stereotype.Service

@Service
class BankAccountService(
    private val bankAccountDatabasePort: BankAccountDatabasePort
) {

    fun saveBankAccount(bankAccount: BankAccount) =
        bankAccountDatabasePort.save(
            bankAccount = bankAccount
        )
}
