package com.kaikeventura.eventsourcingpattern.domain.transaction.service

import com.kaikeventura.eventsourcingpattern.domain.transaction.model.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.transaction.port.out.TransactionEventDatabasePort
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
        transactionEventDatabasePort.findAllByBankAccountIdLimitDate(bankAccountId, limitDate)
}
