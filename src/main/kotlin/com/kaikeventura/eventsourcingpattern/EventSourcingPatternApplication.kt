package com.kaikeventura.eventsourcingpattern

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class EventSourcingPatternApplication

fun main(args: Array<String>) {
	runApplication<EventSourcingPatternApplication>(*args)
}
