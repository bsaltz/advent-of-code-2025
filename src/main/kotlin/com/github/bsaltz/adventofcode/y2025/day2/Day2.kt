package com.github.bsaltz.com.github.bsaltz.adventofcode.y2025.day2

import com.github.bsaltz.com.github.bsaltz.adventofcode.util.Testing

object Day2 {
    fun part1(string: String): Long =
        string.split(",").sumOf { rangeStr ->
            val (start, end) = rangeStr.split("-").map { it.toLong() }
            val range = LongRange(start, end)
            range.asSequence()
                .filter { isInvalid(it.toString(), 2) }
                .sum()
        }

    fun part2(string: String): Long =
        string.split(",").sumOf { rangeStr ->
            val (start, end) = rangeStr.split("-").map { it.toLong() }
            val range = LongRange(start, end)
            range.asSequence()
                .filter { isInvalid(it.toString()) }
                .sum()
        }

    private fun isInvalid(id: String): Boolean =
        (2..id.length).any { isInvalid(id, it) }

    private fun isInvalid(id: String, groups: Int): Boolean {
        // If id can't be split equally into the number of groups, it's valid for this group count
        if (id.length % groups != 0) return false

        // Split id into 'groups' tokens and check if they're all equal, which means it's invalid
        val charsPerGroup = id.length / groups
        return (0..<groups)
            .asSequence()
            .map { id.substring(it * charsPerGroup, (it + 1) * charsPerGroup) }
            .zipWithNext()
            .all { it.first == it.second }
    }
}

fun main(args: Array<String>) {
    Testing.withString("/fixtures/y2025/day2/sample.txt", 1227775554) { Day2.part1(it) }
    Testing.withString("/fixtures/y2025/day2/input.txt") { Day2.part1(it) }
    Testing.withString("/fixtures/y2025/day2/sample.txt", 4174379265) { Day2.part2(it) }
    Testing.withString("/fixtures/y2025/day2/input.txt") { Day2.part2(it) }
}
