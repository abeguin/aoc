package io.github.abeguin.aoc.day1

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component


@Component
@Profile("day1")
class DayOneRunner(
    val dayOneParser: DayOneParser,
    val dayOneCompute: DayOneCompute,
    @Value("\${aoc.day1.input_file}")
    val filePath: ClassPathResource
) : CommandLineRunner {
    val logger: Logger = LoggerFactory.getLogger("Loader")

    override fun run(vararg args: String?) {
        val results = dayOneParser.parseAll(filePath.inputStream.bufferedReader().lines())
        val sum = dayOneCompute.sum(results)
        this.logger.info(sum.toString())
    }
}