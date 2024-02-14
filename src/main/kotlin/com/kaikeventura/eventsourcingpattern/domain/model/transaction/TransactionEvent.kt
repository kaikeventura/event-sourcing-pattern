package com.kaikeventura.eventsourcingpattern.domain.model.transaction

import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.UUID

data class TransactionEvent(
    val id: String? = null,
    val bankAccountId: UUID,
    val transaction: Transaction,
    val createdAt: LocalDateTime? = now()
)
