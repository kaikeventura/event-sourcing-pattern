package com.kaikeventura.eventsourcingpattern.domain.model.transaction

import java.util.UUID

data class TransactionEvent(
    val id: UUID,
    val bankAccountId: UUID,
    val transaction: Transaction
)
