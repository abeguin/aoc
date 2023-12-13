package io.github.abeguin.aoc.day2

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.stream.Collectors


data class Round(
    val red: Int = 0,
    val blue: Int = 0,
    val green: Int = 0,
)

data class Minimum(
    var red: Int = 0,
    var blue: Int = 0,
    var green: Int = 0,
)

data class GameData(
    val id: Int,
    val rounds: Set<Round>
)


@Component
data class GameConfiguration(
    @Value("\${aoc.day2.configuration.availableRed}")
    val availableRed: Int = 0,
    @Value("\${aoc.day2.configuration.availableGreen}")
    val availableGreen: Int = 0,
    @Value("\${aoc.day2.configuration.availableBlue}")
    val availableBlue: Int = 0,
)

@Component
class GameLoader(
    @Value("\${aoc.day2.input_file}")
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

sealed interface Day2Analyzer {
    fun analyze(games: Set<GameData>): Int
}

@Service
@Profile("part1")
class GameAnalyzer(val config: GameConfiguration) : Day2Analyzer {

    private fun isValid(round: Round): Boolean {
        return round.red <= config.availableRed &&
                round.blue <= config.availableBlue &&
                round.green <= config.availableGreen
    }

    private fun isValid(rounds: Set<Round>): Boolean {
        return rounds.map { isValid(it) }
            .reduce { acc, i -> acc && i }
    }

    override fun analyze(games: Set<GameData>): Int {
        return games.map {
            run {
                if (isValid(it.rounds)) it.id else 0
            }
        }.reduce { acc, i -> acc + i }
    }
}

@Service
@Profile("part2")
class GameAnalyzerPart2 : Day2Analyzer {

    private fun foundMinRequirements(rounds: Set<Round>): Minimum {
        val minimum = Minimum()
        rounds.forEach {
            run {
                minimum.red = if (it.red > minimum.red) it.red else minimum.red
                minimum.blue = if (it.blue > minimum.blue) it.blue else minimum.blue
                minimum.green = if (it.green > minimum.green) it.green else minimum.green
            }
        }
        return minimum
    }

    override fun analyze(games: Set<GameData>): Int {
        return games.map {
            run {
                val min = foundMinRequirements(it.rounds)
                min.blue * min.red * min.green

            }
        }.reduce { acc, i -> acc + i }
    }
}



