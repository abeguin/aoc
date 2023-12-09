package io.github.abeguin.aoc.day1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class DayOneParserTest {

    @Nested
    class WordToNumberStr {

        val parser = DayOneParser()

        @Test
        fun `whenOk`() {
            // Given
            val input = "oneioashodiah2"

            // When
            val result = parser.wordToNumberStr(input)


            // Then
            assertEquals("12", result)
        }

        @Test
        fun `when two then ok`() {
            // Given
            val input = "oneioashodiahtwo"

            // When
            val result = parser.wordToNumberStr(input)


            // Then
            assertEquals("12", result)
        }

        @Test
        fun `when eightthree then 83`() {
            // Given
            val input = "eighthree"

            // When
            val result = parser.wordToNumberStr(input)


            // Then
            assertEquals("83", result)
        }
    }

    @Nested
    class Parser {

        val parser = DayOneParser()

        @Test
        fun `day 2 inputs`() {
            // Given
            val inputs = arrayListOf(
                "two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen"
            )

            // When
            val result = inputs.map { parser.parse(it) }

            val expected = arrayOf(29, 83, 13, 24, 42, 14, 76)

            // Then
            result.forEachIndexed { index, i -> assertEquals(expected[index], i) }
        }
    }

    @Nested
    class ParseAll {
        val parser = DayOneParser()

        @Test
        fun `whenOk`() {
            // Given
            val inputs = arrayListOf(
                "two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen"
            ).stream()

            // When
            val results = parser.parseAll(inputs)

            // Then
            assertEquals(results.reduce { acc, i -> acc + i }, 281)
        }
    }
}