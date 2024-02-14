package com.kaikeventura.eventsourcingpattern.domain.port.out.database

import com.kaikeventura.eventsourcingpattern.domain.model.account.BankAccount
import java.util.UUID

interface BankAccountDatabasePort {
    fun save(bankAccount: BankAccount): BankAccount
    fun findById(id: UUID): BankAccount?
}
