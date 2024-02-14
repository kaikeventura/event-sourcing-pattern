package com.kaikeventura.eventsourcingpattern.domain.service

import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.port.out.database.TransactionEventDatabasePort
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class TransactionEventService(
    private val transactionEventDatabasePort: TransactionEventDatabasePort
) {

    fun save(transactionEvent: TransactionEvent): TransactionEvent =
        transactionEventDatabasePort.save(
            transactionEvent = transactionEvent
        )

    fun findAllEventsByBankAccountIdLimit(bankAccountId: UUID, limit: Int): Set<TransactionEvent> =
        transactionEventDatabasePort.findAllByBankAccountIdLimit(bankAccountId, limit)

    fun findAllEventsByBankAccountIdLimitDate(bankAccountId: UUID, limitDate: LocalDateTime): Set<TransactionEvent> =
        transactionEventDatabasePort.findAllByBankAccountIdLimit(bankAccountId, limitDate)
}
