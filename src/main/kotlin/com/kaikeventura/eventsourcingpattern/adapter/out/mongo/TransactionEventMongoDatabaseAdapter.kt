package com.kaikeventura.eventsourcingpattern.adapter.out.mongo

import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.entity.toEntity
import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.entity.toModel
import com.kaikeventura.eventsourcingpattern.adapter.out.mongo.repository.TransactionEventRepository
import com.kaikeventura.eventsourcingpattern.domain.model.transaction.TransactionEvent
import com.kaikeventura.eventsourcingpattern.domain.port.out.database.TransactionEventDatabasePort
import org.springframework.stereotype.Component

@Component
class TransactionEventMongoDatabaseAdapter(
    private val repository: TransactionEventRepository
) : TransactionEventDatabasePort {

    override fun save(transactionEvent: TransactionEvent): TransactionEvent =
        repository.save(transactionEvent.toEntity()).toModel()
}
