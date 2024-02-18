package com.kaikeventura.eventsourcingpattern.domain.account.port.out

import com.kaikeventura.eventsourcingpattern.domain.account.model.BankAccount
import java.util.UUID

interface BankAccountDatabasePort {
    fun save(bankAccount: BankAccount): BankAccount
    fun findById(id: UUID): BankAccount?
    fun existsById(id: UUID): Boolean
    fun findCurrentBalanceById(bankAccountId: UUID): Long?
}
