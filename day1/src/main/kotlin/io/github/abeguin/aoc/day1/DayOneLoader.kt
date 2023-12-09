package io.github.abeguin.aoc.day1

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component


@Component
class DayOneLoader(
    val dayOneParser: DayOneParser,
    val dayOneCompute: DayOneCompute,
    @Value("\${app.input_file}")
    val filePath: ClassPathResource
) : CommandLineRunner {
    val logger: Logger = LoggerFactory.getLogger("Loader")

    override fun run(vararg args: String?) {
        val results = dayOneParser.parseAll(filePath.inputStream.bufferedReader().lines())
        val sum = dayOneCompute.sum(results)
        this.logger.info(sum.toString())
    }
}