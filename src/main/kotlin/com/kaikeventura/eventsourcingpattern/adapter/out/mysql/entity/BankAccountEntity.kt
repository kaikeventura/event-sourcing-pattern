package com.kaikeventura.eventsourcingpattern.adapter.out.mysql.entity

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.annotation.Id

data class BankAccountEntity(
    @Id
    val id: UUID? = null,
    val balance: Long,
    val name: String,
    val document: String,
    val birthDate: LocalDate,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
)
