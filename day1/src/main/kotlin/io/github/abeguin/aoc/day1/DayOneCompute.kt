package io.github.abeguin.aoc.day1

import org.springframework.stereotype.Service


@Service
class DayOneCompute {

    fun sum(values: List<Int>): Int {
        return values.stream().reduce { t, u -> t + u }.get()
    }

}