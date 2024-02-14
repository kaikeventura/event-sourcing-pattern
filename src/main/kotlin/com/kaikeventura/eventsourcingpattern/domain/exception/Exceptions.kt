package com.kaikeventura.eventsourcingpattern.domain.exception

import java.util.UUID

class InsufficientBalanceException(bankAccountId: UUID):
    RuntimeException("Insufficient balance for bank account $bankAccountId")
