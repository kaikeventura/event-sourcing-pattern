package com.kaikeventura.eventsourcingpattern.factory

import com.kaikeventura.eventsourcingpattern.domain.model.account.BankAccount
import java.time.LocalDate

fun aBankAccount(
): BankAccount =
    BankAccount(
        name = "Donnie",
        document = "123",
        birthDate = LocalDate.parse("2000-01-01")
    )
