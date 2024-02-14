package com.kaikeventura.eventsourcingpattern.domain.port.out.database

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent

interface TransactionEventDatabasePort {
    fun save(transactionEvent: TransactionEvent): TransactionEvent
}
