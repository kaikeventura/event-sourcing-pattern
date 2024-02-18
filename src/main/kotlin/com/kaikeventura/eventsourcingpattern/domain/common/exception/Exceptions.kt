package com.kaikeventura.eventsourcingpattern.domain.common.exception

import java.util.UUID

class InsufficientBalanceException(bankAccountId: UUID):
    RuntimeException("Insufficient balance for bank account $bankAccountId")

class BankAccountNotFoundException(bankAccountId: UUID):
    RuntimeException("Bank account $bankAccountId not found")
