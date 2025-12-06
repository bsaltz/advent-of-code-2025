package com.github.bsaltz.com.github.bsaltz.adventofcode.y2025.day1

import com.github.bsaltz.com.github.bsaltz.adventofcode.util.Testing

object Day1Part1 {
    fun calculatePassword(lines: Sequence<String>): Int {
        var count = 0
        var dial = 50
        lines.forEach { line ->
            val amount = parse(line) ?: 0
            val next = (dial + amount) % 100
            dial = if (next < 0) {
                next + 100
            } else {
                next
            }
            if (dial == 0) {
                count++
            }
        }
        return count
    }

    private fun parse(line: String): Int? =
        line.takeIf { it.isNotBlank() }?.let {
            when (line[0]) {
                'L' -> -line.substring(1).toInt()
                'R' -> line.substring(1).toInt()
                else -> error("Could not parse: '$line'")
            }
        }
}

object Day1Part2 {
    fun calculatePassword(lines: Sequence<String>): Int {
        var count = 0
        var dial = 50
        lines.forEach { line ->
            val (dir, amount) = parse(line) ?: ('L' to 0)
            if (dir == 'L') {
                repeat(amount) {
                    dial--
                    if (dial < 0) {
                        dial += 100
                    }
                    if (dial == 0) {
                        count++
                    }
                }
            } else if (dir == 'R') {
                repeat(amount) {
                    dial++
                    if (dial > 99) {
                        dial -= 100
                    }
                    if (dial == 0) {
                        count++
                    }
                }
            }
        }
        return count
    }

    private fun parse(line: String): Pair<Char, Int>? =
        line.takeIf { it.isNotBlank() }?.let { line[0] to line.substring(1).toInt() }
}

fun main(args: Array<String>) {
    Testing.withSequence("/fixtures/y2025/day1/sample.txt", 3) { Day1Part1.calculatePassword(it) }
    Testing.withSequence("/fixtures/y2025/day1/input.txt") { Day1Part1.calculatePassword(it) }
    Testing.withSequence("/fixtures/y2025/day1/sample.txt", 6) { Day1Part2.calculatePassword(it) }
    Testing.withSequence("/fixtures/y2025/day1/input.txt") { Day1Part2.calculatePassword(it) }
}