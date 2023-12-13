package io.github.abeguin.aoc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AOCApplication

fun main(args: Array<String>) {
    runApplication<AOCApplication>(*args)
}