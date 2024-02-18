package com.kaikeventura.eventsourcingpattern.domain.transaction.model

import java.time.LocalDateTime
import java.util.UUID

data class TransactionEvent(
    val id: String? = null,
    val bankAccountId: UUID,
    val transaction: Transaction,
    val occurredAt: LocalDateTime
)
