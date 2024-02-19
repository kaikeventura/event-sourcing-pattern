# Event Sourcing Microservice

Este repositório contém um microsserviço desenvolvido para implementar o padrão Event Sourcing nas entidades `BankAccount` e `Transaction`. O Event Sourcing é aplicado especificamente às transações, registrando eventos cada vez que uma transação de depósito ou saque é realizada. Os eventos são armazenados como documentos no MongoDB, enquanto as informações da conta bancária são persistentes no MySQL.

# Domínio
### BankAccount
Representação básica para a criação de uma conta.
``` kotlin
data class BankAccount(  
	val id: UUID? = null,  
	val balance: Long = 0L,  
	val name: String,  
	val document: String,  
	val birthDate: LocalDate  
)
```
### BankStatement
Duas classes para representação do extrato bancário de uma conta.
``` kotlin
data class BankStatement(  
	val bankAccountId: UUID,  
	val currentBalance: Long,  
	val bankStatementDate: LocalDateTime = now(),  
	val statements: Set<Statement>  
)  

data class Statement(  
	val id: String,  
	val value: Long,  
	val description: String,  
	val occurredAt: LocalDateTime  
)
```
### Transaction
A interface `Transaction` define a estrutura básica de uma transação com os seguintes atributos:
-   `totalValue`: Valor total da transação.
-   `operation`: Operação da transação (aumento ou diminuição do saldo).
-   `description`: Descrição da transação.
-   `occurredAt`: Data e hora da ocorrência da transação.

Existem duas implementações concretas da interface `Transaction`:
-   `DepositTransaction`: Representa uma transação de depósito.
-   `WithdrawTransaction`: Representa uma transação de saque.

A enumeração `TransactionOperation` define os tipos de operações possíveis e suas lógicas de cálculo.
``` kotlin
interface Transaction {  
    val totalValue: Long  
    val operation: TransactionOperation  
    val description: String  
    val occurredAt: LocalDateTime  
}  
  
data class DepositTransaction(  
	override val totalValue: Long,  
	override val operation: TransactionOperation,  
	override val description: String,  
	override val occurredAt: LocalDateTime  
) : Transaction {  
	constructor(  
		depositValue: Long,  
		occurredAt: LocalDateTime = now()  
	): this(  
		totalValue = depositValue,  
		operation = INCREASE,  
		description = "Deposit R$ $depositValue",  
		occurredAt = occurredAt  
	)  
}  
  
data class WithdrawTransaction(  
	override val totalValue: Long,  
	override val operation: TransactionOperation,  
	override val description: String,  
	override val occurredAt: LocalDateTime  
) : Transaction {  
	constructor(  
		withdrawValue: Long,  
		occurredAt: LocalDateTime = now()  
	): this(  
		totalValue = withdrawValue,  
		operation = DECREASE,  
		description = "Withdraw R$ $withdrawValue",  
		occurredAt = occurredAt  
	)  
}
  
enum class TransactionOperation {  
	
	INCREASE {  
		override fun calculate(currentValue: Long, operationValue: Long): Long = currentValue.plus(operationValue)  
	},  
	DECREASE {  
		override fun calculate(currentValue: Long, operationValue: Long): Long = currentValue.minus(operationValue)  
	};  

	abstract fun calculate(currentValue: Long, operationValue: Long): Long  
}
```
# Entidades
### BankAccount
A entidade `BankAccount` representa uma conta bancária com os seguintes atributos:
-   `id`: Identificador único da conta (UUID).
-   `balance`: Saldo atual da conta.
-   `name`: Nome do titular da conta.
-   `document`: Documento do titular da conta.
-   `birthDate`: Data de nascimento do titular da conta.
-   `createdAt`: Data de criação do registro.
-   `modifiedAt`: Data da última modificação do registro.
``` kotlin
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
```
### TransactionEvent
A entidade `TransactionEvent` representa um evento de transação com os seguintes atributos:
-   `id`: Identificador único do evento, gerado pelo MongoDB (String).
-   `bankAccountId`: Id da conta que o evento pertence.
-   `transaction`: Interface que será representa cada tipo de transação, conforme implementação.
-   `occurredAt`: Data em que a transação ocorreu.
-   `createdAt`: Data de criação do registro.
-   `modifiedAt`: Data da última modificação do registro.
``` kotlin
@Document(collection = "transaction_event")  
data class TransactionEventEntity(  
	@Id  
	val id: String? = null,  

	val bankAccountId: UUID,  
	val transaction: Transaction,  
	val occurredAt: LocalDateTime,  

	@CreatedDate  
	@Column(updatable = false)  
	val createdAt: LocalDateTime? = null,  

	@LastModifiedDate  
	val modifiedAt: LocalDateTime? = null  
)
```
## Endpoints

### BankAccount:
-   POST `/bank-account`: Cria uma nova conta bancária:
    Request:
    ``` json
    {
      "name": "Donnie Donizete",
      "document": "12345678900",
      "birthDate": "1990-05-15"
    }
    ```
    Response:
    ``` json
    {
      "id": "a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6",
      "balance": 0,
      "name": "Donnie Donizete",
      "document": "12345678900",
      "birthDate": "1990-05-15"
    }
    ```
-   GET `/bank-account/{bankAccountId}/bank-statement?limit=10`: Retorna o extrato das últimas transações de uma conta com base no número limite de itens especificado:
    Response:
    ``` json
    {
      "bankAccountId": "a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6",
      "currentBalance": 2000,
      "bankStatementDate": "2024-02-18T20:00:00",
      "statements": [
        {
          "id": "65d29ccfc5239bf91acb0c87",
          "value": 3000,
          "description": "Withdraw R$ 3000",
          "occurredAt": "2024-02-18T15:00:00"
        },
        {
          "id": "5d29cd6ecfa5955b12c8119",
          "value": 5000,
          "description": "Deposit R$ 5000",
          "occurredAt": "2024-02-18T12:30:00"
        }
      ]
    }
    ```
-   GET `/bank-account/{bankAccountId}/balance-on-date?referenceDate=2024-02-18T14:00:00`: Reconstrói o estado do saldo da conta para a data de referência, utilizando o Event Sourcing Pattern:
    Response:
    ``` json
    {
      "referenceDate": "2024-02-18T14:00:00",
      "balance": 5000
    }
    ```
### Transaction:
-   POST `/transaction/bankAccount/{bankAccountId}/deposit`: Cria uma transação de depósito.
    Request:
    ``` json
    {
      "totalValue": 5000,
      "occurredAt": "2024-02-18T12:30:00"
    }
    ```
    Response:
    ``` json
    {
      "transactionId": "65d29f48422363f536059876"
    }
    ```
-   POST `/transaction/bankAccount/{bankAccountId}/withdraw`: Cria uma transação de saque:
    Request:
    ``` json
    {
      "totalValue": 3000,
      "occurredAt": "2024-02-18T15:00:00"
    }
    ```
    Response:
    ``` json
    {
      "transactionId": "65d29f7649ecaa0cbb52690e"
    }
    ```
    
## Como Executar
1.  Clone este repositório.
2.  Suba os databases através do docker compose:
    ``` bash
    docker compose up -d
    ```
3.  Execute a aplicação.
    ``` bash
    ./gradlew bootRun 
    ```
A aplicação estará disponível em http://localhost:8080.
