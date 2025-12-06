package com.github.bsaltz.com.github.bsaltz.adventofcode.util

import java.io.InputStream

object Testing {
    fun <T> withList(fixture: String, expected: T? = null, block: (List<String>) -> T) {
        doTest(fixture, expected, block) { it.bufferedReader().readLines() }
    }

    fun <T> withString(fixture: String, expected: T? = null, block: (String) -> T) {
        doTest(fixture, expected, block) { it.bufferedReader().readText() }
    }

    fun <T> withSequence(fixture: String, expected: T? = null, block: (Sequence<String>) -> T) {
        doTest(fixture, expected, block) { it.bufferedReader().lineSequence() }
    }

    private fun <T, I> doTest(fixture: String, expected: T?, block: (I) -> T, streamMapper: (InputStream) -> I) {
        val result = run(fixture, block, streamMapper)
        reportResult(expected, result)
    }

    private fun <T, I> run(fixture: String, block: (I) -> T, streamMapper: (InputStream) -> I) =
        inputStream(fixture).use { streamMapper(it).let(block) }

    private fun inputStream(fixture: String): InputStream =
        javaClass.getResourceAsStream(fixture) ?: error("fixture not found: $fixture")

    private fun <T> reportResult(expected: T?, result: T) {
        if (expected != null) {
            if (result != expected) {
                println("[FAIL] expected '$expected', got '$result'")
            } else {
                println("[PASS] expected '$expected', got '$result'")
            }
        } else {
            println("[TEST] got '$result'")
        }
    }
}
