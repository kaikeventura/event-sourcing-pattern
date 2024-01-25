package com.kaikeventura.eventsourcingpattern

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EventSourcingPatternApplication

fun main(args: Array<String>) {
	runApplication<EventSourcingPatternApplication>(*args)
}
