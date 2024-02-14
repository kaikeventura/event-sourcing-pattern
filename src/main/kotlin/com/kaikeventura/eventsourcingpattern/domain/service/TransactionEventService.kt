package com.kaikeventura.eventsourcingpattern.domain.service

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.port.out.database.TransactionEventDatabasePort
import org.springframework.stereotype.Service

@Service
class TransactionEventService(
    private val transactionEventDatabasePort: TransactionEventDatabasePort
) {

    fun save(transactionEvent: TransactionEvent): TransactionEvent =
        transactionEventDatabasePort.save(
            transactionEvent = transactionEvent
        )
}
