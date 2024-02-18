package com.kaikeventura.eventsourcingpattern.adapter.out.database.mysql.entity

import com.kaikeventura.eventsourcingpattern.domain.account.model.BankAccount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity(name = "bank_account")
data class BankAccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    val balance: Long,
    val name: String,
    val document: String,
    val birthDate: LocalDate,

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    val modifiedAt: LocalDateTime? = null
)

fun BankAccountEntity.toModel(): BankAccount =
    BankAccount(
        id = id,
        balance = balance,
        name = name,
        document = document,
        birthDate = birthDate
    )

fun BankAccount.toEntity(): BankAccountEntity =
    BankAccountEntity(
        id = id?.let { id },
        balance = balance,
        name = name,
        document = document,
        birthDate = birthDate
    )
