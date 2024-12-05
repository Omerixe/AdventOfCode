package `2022`

import java.io.File


fun main() {

    val input = File(ClassLoader.getSystemResource("2022/input07.txt").toURI())
        .readLines()

    Day07.part1(input)
    Day07.part2(input)
}

object Day07 {

    private fun parseInput(input: List<String>): File {
        val treeParent = File("/", true, mutableListOf(), null)
        var tree = treeParent
        input.forEach { line ->
            if (line.startsWith("$ cd ")) {
                val dir = line.replace("$ cd ", "")
                when (dir) {
                    ".." -> {
                        tree = tree.parent ?: tree
                    }

                    "/" -> {}
                    else -> {
                        tree = tree.contents.first { it.isDirectory && it.name == dir }
                    }
                }
            } else if (!line.startsWith("\$ ls")) {
                val parts = line.split(" ")
                when (parts[0]) {
                    "dir" -> {
                        if (tree.contents.filter { it.isDirectory && it.name == parts[1] }.isEmpty()) {
                            tree.contents.add(File(parts[1], true, mutableListOf(), tree))
                        }
                    }

                    else -> {
                        if (tree.contents.filter { !(it.isDirectory) && it.name == parts[1] }.isEmpty()) {
                            tree.contents.add(File(parts[1], false, mutableListOf(), tree, parts[0].toInt()))
                        }
                    }
                }
            }
        }
        return treeParent
    }

    fun part1(input: List<String>) {
        val treeParent = parseInput(input)
        val result = treeParent.allDirSizes().filter { it < 100000 }.sum()
        println("Part1: $result")
    }

    fun part2(input: List<String>) {
        val totalSpace = 70000000
        val unusedSpaceNeeded = 30000000
        val treeParent = parseInput(input)
        val freeUp = (totalSpace - treeParent.size - unusedSpaceNeeded) * -1

        val result = treeParent.allDirSizes().filter { it >= freeUp }.minOf { it }
        println("Part2: $result")
    }

    private fun File.allDirSizes(): List<Int> {
        return if (isDirectory) {
            return listOf(size) + contents.flatMap { it.allDirSizes() }
        } else {
            listOf()
        }
    }

    data class File(
        val name: String,
        val isDirectory: Boolean,
        val contents: MutableList<File>,
        val parent: File?,
        val length: Int = 0
    ) {
        val size: Int
            get() {
                return if (!isDirectory) {
                    length
                } else {
                    contents.sumOf { it.size }
                }
            }
    }
}
