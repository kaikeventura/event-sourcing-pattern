package com.kaikeventura.eventsourcingpattern.domain.model.account

import java.time.LocalDate
import java.util.UUID

data class BankAccount(
    val id: UUID? = null,
    val balance: Long = 0L,
    val name: String,
    val document: String,
    val birthDate: LocalDate
)
