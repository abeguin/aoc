package io.github.abeguin.aoc.day2

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("day2")
class GameRunner(
    val analyzer: GameAnalyzer,
    val loader: GameLoader
) : CommandLineRunner {

    val logger: Logger = LoggerFactory.getLogger("GameRunner")

    override fun run(vararg args: String?) {
        val games = loader.load()
        val result = analyzer.analyze(games)
        logger.info("Result = $result")
    }
}