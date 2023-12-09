package io.github.abeguin.aoc.day2

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.stream.Collectors


data class Round(
    val red: Int = 0,
    val blue: Int = 0,
    val green: Int = 0,
)

data class GameData(
    val id: Int,
    val rounds: Set<Round>
)


@Component
data class GameConfiguration(
    @Value("\${app.game.configuration.availableRed}")
    val availableRed: Int = 0,
    @Value("\${app.game.configuration.availableGreen}")
    val availableGreen: Int = 0,
    @Value("\${app.game.configuration.availableBlue}")
    val availableBlue: Int = 0,
)

@Component
class GameLoader(
    @Value("\${app.game.inputs}")
    val inputs: ClassPathResource
) {

    fun parseLine(line: String): GameData {
        val id = Regex("[0-9]+").find(line.split(":")[0])?.value?.toInt()
        val rounds = line.split(":")[1].split(";")
        val gameRounds = rounds.map {
            run {
                val red = Regex("[0-9]+ red").find(it)?.value?.split(" ")?.get(0)?.toInt() ?: 0
                val green = Regex("[0-9]+ green").find(it)?.value?.split(" ")?.get(0)?.toInt() ?: 0
                val blue = Regex("[0-9]+ blue").find(it)?.value?.split(" ")?.get(0)?.toInt() ?: 0

                Round(red = red, green = green, blue = blue)
            }
        }.toSet()
        return GameData(id = id!!, rounds = gameRounds)
    }

    fun load(): Set<GameData> {
        return inputs.inputStream.bufferedReader()
            .lines()
            .map { parseLine(it) }
            .collect(Collectors.toSet())
    }

}


@Service
class GameAnalyzer(val config: GameConfiguration) {

    private fun isValid(round: Round): Boolean {
        return round.red <= config.availableRed &&
                round.blue <= config.availableBlue &&
                round.green <= config.availableGreen
    }

    private fun isValid(rounds: Set<Round>): Boolean {
        return rounds.map { isValid(it) }
            .reduce { acc, i -> acc && i }
    }

    fun analyze(games: Set<GameData>): Int {
        return games.map {
            run {
                if (isValid(it.rounds)) it.id else 0
            }
        }.reduce { acc, i -> acc + i }
    }
}


@Component
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


