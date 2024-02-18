package com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto

import com.kaikeventura.eventsourcingpattern.domain.account.model.BankAccount
import java.time.LocalDate
import java.util.UUID

data class BankAccountRequestDTO(
    val name: String,
    val document: String,
    val birthDate: LocalDate
) {
    fun toModel(): BankAccount =
        BankAccount(
            name = name,
            document = document,
            birthDate = birthDate
        )
}

data class BankAccountResponseDTO(
    val id: UUID,
    val balance: Long,
    val name: String,
    val document: String,
    val birthDate: LocalDate
)

fun BankAccount.toDTO(): BankAccountResponseDTO =
    BankAccountResponseDTO(
        id = id!!,
        balance = balance,
        name = name,
        document = document,
        birthDate = birthDate
    )
