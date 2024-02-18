package com.kaikeventura.eventsourcingpattern.adapter.`in`.controller

import com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto.BalanceResponseDTO
import com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto.BankAccountRequestDTO
import com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto.BankAccountResponseDTO
import com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto.BankStatementResponseDTO
import com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto.toDTO
import com.kaikeventura.eventsourcingpattern.domain.account.port.`in`.BankAccountPort
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bank-account")
class BankAccountController(
    private val bankAccountPort: BankAccountPort
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun createBankAccount(@RequestBody bankAccountRequestDTO: BankAccountRequestDTO): BankAccountResponseDTO =
        bankAccountPort.createBankAccount(
            bankAccount = bankAccountRequestDTO.toModel()
        ).toDTO()

    @GetMapping("/{bankAccountId}/bank-statement")
    @ResponseStatus(OK)
    fun getBankStatement(
        @PathVariable("bankAccountId") bankAccountId: UUID,
        @RequestParam("limit") limit: Int
    ): BankStatementResponseDTO =
        bankAccountPort.getBankStatementByBankAccountId(
            bankAccountId = bankAccountId,
            limit = limit
        ).toDTO()

    @GetMapping("/{bankAccountId}/balance-on-date")
    @ResponseStatus(OK)
    fun getBalanceOnDate(
        @PathVariable("bankAccountId") bankAccountId: UUID,
        @RequestParam("referenceDate") referenceDate: LocalDateTime
    ): BalanceResponseDTO =
        bankAccountPort.rebuildBankAccountBalanceUntil(
            bankAccountId = bankAccountId,
            referenceDate = referenceDate
        ).toDTO()
}
