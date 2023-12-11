package io.violabs.gremlins

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GremlinsApplication

fun main(args: Array<String>) {
	runApplication<GremlinsApplication>(*args)
}
