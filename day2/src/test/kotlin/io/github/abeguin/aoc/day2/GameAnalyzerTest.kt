package io.github.abeguin.aoc.day2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GameAnalyzerTest {


    private val configuration = GameConfiguration(
        availableRed = 10,
        availableGreen = 5,
        availableBlue = 8
    )
    private val analyzer = GameAnalyzer(configuration)

    @Test
    fun `given a valid game should return 1`() {
        // Given
        val game = GameData(
            id = 1, rounds = setOf(
                Round(red = 1, blue = 2, green = 3)
            )
        )

        // When
        var result = analyzer.analyze(setOf(game))

        // Then
        assertEquals(1, result)
    }

    @Test
    fun `given a the example games should return 1`() {
        // Given
        val games = setOf(
            GameData(
                id = 1,
                rounds = setOf(
                    Round(red = 4, blue = 3),
                    Round(red = 1, green = 2, blue = 6),
                    Round(green = 2),
                )
            ),
            GameData(
                id = 2,
                rounds = setOf(
                    Round(green = 2, blue = 1),
                    Round(red = 1, green = 3, blue = 4),
                    Round(green = 1, blue = 1),
                )
            ),
            GameData(
                id = 3,
                rounds = setOf(
                    Round(green = 8, blue = 6, red = 20),
                    Round(red = 4, green = 13, blue = 5),
                    Round(green = 5, red = 1),
                )
            ),
            GameData(
                id = 4,
                rounds = setOf(
                    Round(green = 1, blue = 6, red = 3),
                    Round(red = 6, green = 3),
                    Round(green = 3, red = 14, blue = 15),
                )
            ),
            GameData(
                id = 5,
                rounds = setOf(
                    Round(green = 3, blue = 1, red = 6),
                    Round(red = 1, green = 2, blue = 2),
                )
            )
        )

        // When
        var result = analyzer.analyze(games = games)

        // Then
        assertEquals(8, result)
    }
}