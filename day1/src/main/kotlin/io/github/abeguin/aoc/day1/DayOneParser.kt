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

        var acc = line
        numbers.forEachIndexed { index, num ->
            val regex = Regex(num, option = RegexOption.IGNORE_CASE)
            val m = regex.find(acc)
            if (m != null) {
                matches.add(
                    OrderedNumber(
                        representation = num,
                        value = index,
                        index = m.range.first
                    )
                )
            }

            /*
            val m = regex.findAll(acc)
            m.forEach { mm ->
                matches.add(
                    OrderedNumber(
                        representation = num,
                        value = index,
                        index = mm.range.first
                    )
                )
            }
             */
        }

        val sortedMatches = matches.sortedBy { it.index }

        sortedMatches.forEach { it ->
            acc = acc.replace(it.representation, "${it.value}", ignoreCase = true)
        }
        return acc
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