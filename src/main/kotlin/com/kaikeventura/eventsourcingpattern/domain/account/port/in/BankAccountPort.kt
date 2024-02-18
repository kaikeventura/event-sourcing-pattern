package com.kaikeventura.eventsourcingpattern.domain.account.port.`in`

import com.kaikeventura.eventsourcingpattern.domain.account.model.BankAccount
import com.kaikeventura.eventsourcingpattern.domain.account.model.BankStatement
import java.time.LocalDateTime
import java.util.UUID

interface BankAccountPort {
    fun createBankAccount(bankAccount: BankAccount): BankAccount
    fun getBankStatementByBankAccountId(bankAccountId: UUID, limit: Int = 10): BankStatement
    fun rebuildBankAccountBalanceUntil(bankAccountId: UUID, referenceDate: LocalDateTime): Pair<LocalDateTime, Long>
}
