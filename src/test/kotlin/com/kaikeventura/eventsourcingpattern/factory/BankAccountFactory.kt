package com.kaikeventura.eventsourcingpattern.factory

import com.kaikeventura.eventsourcingpattern.domain.account.model.BankAccount
import java.time.LocalDate

fun aBankAccount(
    document: String = "123"
): BankAccount =
    BankAccount(
        name = "Donnie",
        document = document,
        birthDate = LocalDate.parse("2000-01-01")
    )
