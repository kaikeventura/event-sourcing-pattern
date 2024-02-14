package com.kaikeventura.eventsourcingpattern.adapter.out.mysql

import com.kaikeventura.eventsourcingpattern.adapter.out.mysql.entity.toEntity
import com.kaikeventura.eventsourcingpattern.adapter.out.mysql.entity.toModel
import com.kaikeventura.eventsourcingpattern.adapter.out.mysql.repository.BankAccountRepository
import com.kaikeventura.eventsourcingpattern.domain.model.account.BankAccount
import com.kaikeventura.eventsourcingpattern.domain.port.out.database.BankAccountDatabasePort
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class BankAccountMySqlDatabaseAdapter(
    private val repository: BankAccountRepository
): BankAccountDatabasePort {

    override fun save(bankAccount: BankAccount): BankAccount =
        repository.save(bankAccount.toEntity()).toModel()

    override fun findById(id: UUID): BankAccount? =
        repository.findByIdOrNull(id)?.toModel()

    override fun findCurrentBalanceById(bankAccountId: UUID): Long? =
        repository.balanceById(bankAccountId)
}
