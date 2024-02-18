package com.kaikeventura.eventsourcingpattern.domain.transaction.port.`in`

import com.kaikeventura.eventsourcingpattern.domain.transaction.model.Transaction
import java.util.UUID

interface TransactionPort {
    fun createTransaction(bankAccountId: UUID, transaction: Transaction): String
}
