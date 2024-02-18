package com.kaikeventura.eventsourcingpattern.domain.transaction.port.out

import com.kaikeventura.eventsourcingpattern.domain.transaction.model.TransactionEvent
import java.time.LocalDateTime
import java.util.UUID

interface TransactionEventDatabasePort {
    fun save(transactionEvent: TransactionEvent): TransactionEvent
    fun findAllByBankAccountIdLimit(bankAccountId: UUID, limit: Int): Set<TransactionEvent>
    fun findAllByBankAccountIdLimitDate(bankAccountId: UUID, limitDate: LocalDateTime): Set<TransactionEvent>
}
