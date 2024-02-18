package com.kaikeventura.eventsourcingpattern.adapter.out.database.mysql

import com.kaikeventura.eventsourcingpattern.adapter.out.database.mysql.entity.toEntity
import com.kaikeventura.eventsourcingpattern.adapter.out.database.mysql.entity.toModel
import com.kaikeventura.eventsourcingpattern.adapter.out.database.mysql.repository.BankAccountRepository
import com.kaikeventura.eventsourcingpattern.domain.account.model.BankAccount
import com.kaikeventura.eventsourcingpattern.domain.account.port.out.BankAccountDatabasePort
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

    override fun existsById(id: UUID): Boolean =
        repository.existsById(id)

    override fun findCurrentBalanceById(bankAccountId: UUID): Long? =
        repository.balanceById(bankAccountId)
}
