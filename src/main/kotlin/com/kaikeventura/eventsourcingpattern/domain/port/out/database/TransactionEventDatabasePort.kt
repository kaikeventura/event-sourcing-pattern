package com.kaikeventura.eventsourcingpattern.domain.port.out.database

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import java.util.UUID

interface TransactionEventDatabasePort {
    fun save(transactionEvent: TransactionEvent): TransactionEvent
    fun findAllByBankAccountIdLimit(bankAccountId: UUID, limit: Int): Set<TransactionEvent>
}
