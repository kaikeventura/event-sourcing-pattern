package com.kaikeventura.eventsourcingpattern.adapter.`in`.controller

import com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto.DepositRequestDTO
import com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto.TransactionResponseDTO
import com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto.WithdrawRequestDTO
import com.kaikeventura.eventsourcingpattern.adapter.`in`.controller.dto.toDTO
import com.kaikeventura.eventsourcingpattern.domain.transaction.port.`in`.TransactionPort
import java.util.UUID
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transaction")
class TransactionController(
    private val transactionPort: TransactionPort
) {

    @PostMapping("/bankAccount/{bankAccountId}/deposit")
    @ResponseStatus(CREATED)
    fun createDepositTransaction(
        @PathVariable("bankAccountId") bankAccountId: UUID,
        @RequestBody depositRequestDTO: DepositRequestDTO
    ): TransactionResponseDTO =
        transactionPort.createTransaction(
            bankAccountId = bankAccountId,
            transaction = depositRequestDTO.toModel()
        ).toDTO()

    @PostMapping("/bankAccount/{bankAccountId}/withdraw")
    @ResponseStatus(CREATED)
    fun createWithdrawTransaction(
        @PathVariable("bankAccountId") bankAccountId: UUID,
        @RequestBody withdrawRequestDTO: WithdrawRequestDTO
    ): TransactionResponseDTO =
        transactionPort.createTransaction(
            bankAccountId = bankAccountId,
            transaction = withdrawRequestDTO.toModel()
        ).toDTO()
}
