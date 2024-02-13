package com.kaikeventura.eventsourcingpattern.domain.port.out.database

import com.kaikeventura.eventsourcingpattern.domain.model.account.BankAccount

interface BankAccountDatabasePort {
    fun save(bankAccount: BankAccount): BankAccount
}
