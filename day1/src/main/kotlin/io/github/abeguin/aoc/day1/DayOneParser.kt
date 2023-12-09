package io.github.abeguin.aoc.day1

import org.springframework.stereotype.Service
import java.util.stream.Stream


data class OrderedNumber(val representation: String, val value: Int, val index: Int)

@Service
class DayOneParser {

    private val numbers =
        arrayListOf(
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine"
        )

    fun wordToNumberStr(line: String): String {
        var matches: ArrayList<OrderedNumber> = ArrayList()

        numbers.forEachIndexed { index, num ->
            val regex = Regex(num, option = RegexOption.IGNORE_CASE)
            val m = regex.findAll(line)
            m.forEach { mm ->
                matches.add(
                    OrderedNumber(
                        representation = num,
                        value = index,
                        index = mm.range.first
                    )
                )
            }
        }

        val regex = Regex("[0-9]")
        val numericMatches = regex.findAll(line)
        numericMatches.forEach { m ->
            matches.add(
                OrderedNumber(
                    representation = m.value,
                    value = m.value.toInt(),
                    index = m.range.first
                )
            )
        }

        val sortedMatches = matches.sortedBy { it.index }

        return sortedMatches.map { it.value }.joinToString(separator = "")
    }


    fun parse(line: String): Int {
        val digits = wordToNumberStr(line).filter { c -> c.isDigit() }
        return try {
            when (digits.length) {
                0 -> 0
                1 -> "$digits$digits".toInt()
                2 -> digits.toInt()
                else -> "${digits[0]}${digits[digits.length - 1]}".toInt()
            }
        } catch (e: NumberFormatException) {
            0
        }
    }

    fun parseAll(lines: Stream<String>): List<Int> {
        return lines.map { parse(it) }.toList()
    }
}