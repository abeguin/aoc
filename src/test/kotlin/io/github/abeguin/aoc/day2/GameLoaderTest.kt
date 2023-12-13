package io.github.abeguin.aoc.day2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource

class GameLoaderTest {

    // Given
    private val input = ClassPathResource("data/day2.txt")
    private val loader = GameLoader(input)

    @Test
    fun `when data parsing should be ok`() {
        // When
        val line = loader.inputs.inputStream.bufferedReader().readLine()
        val result = loader.parseLine(line)

        // Then
        val round1 = Round(blue = 3, red = 4)
        val round2 = Round(blue = 6, red = 1, green = 2)
        val round3 = Round(green = 2)

        assertEquals(1, result.id)
        assertTrue(result.rounds.containsAll(setOf(round1, round2, round3)))
    }

    @Test
    fun `when data parsing id should be ok`() {
        // When
        val line = "Game 25: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        val result = loader.parseLine(line)

        // Then
        assertEquals(25, result.id)
    }
}