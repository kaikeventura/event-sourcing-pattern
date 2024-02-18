package com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto

import java.time.LocalDateTime

data class BalanceResponseDTO(
    val referenceDate: LocalDateTime,
    val balance: Long
)

fun Pair<LocalDateTime, Long>.toDTO(): BalanceResponseDTO =
    BalanceResponseDTO(
        referenceDate = first,
        balance = second
    )
