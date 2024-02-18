package com.kaikeventura.eventsourcingpattern.domain.account.service

import com.kaikeventura.eventsourcingpattern.domain.account.model.BankAccount
import com.kaikeventura.eventsourcingpattern.domain.account.port.out.BankAccountDatabasePort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class BankAccountService(
    private val bankAccountDatabasePort: BankAccountDatabasePort
) {

    fun saveBankAccount(bankAccount: BankAccount) =
        bankAccountDatabasePort.save(
            bankAccount = bankAccount
        )

    fun findBankAccountById(id: UUID) =
        bankAccountDatabasePort.findById(id)

    fun existsByAccountById(id: UUID) =
        bankAccountDatabasePort.existsById(id)

    fun findCurrentBalanceByBankAccountId(bankAccountId: UUID) =
        bankAccountDatabasePort.findCurrentBalanceById(bankAccountId)
}
