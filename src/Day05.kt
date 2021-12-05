fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map(convertToLine)
            .filter { it.isHorizontal || it.isVertical }
            .map(Line::createLine)
            .flatten()
            .groupingBy { it }
            .eachCount()
            .count { it.value >= 2 }
    }

    fun part2(input: List<String>): Int {
        return input
            .map(convertToLine)
            .map(Line::createLine)
            .flatten()
            .groupingBy { it }
            .eachCount()
            .count { it.value >= 2 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)

    val input = readInput("Day05")
    println(part1(input))

    val testInput2 = readInput("Day05_test")
    check(part2(testInput2) == 12)
    println(part2(input))
}

data class Point(val x: Int, val y: Int)

data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    val isHorizontal = y1 == y2
    val isVertical = x1 == x2

    fun createLine(): List<Point> = mutableListOf<Point>().apply {
        return when {
            isVertical -> createVerticalLine()
            isHorizontal -> createHorizontalLine()
            else -> createDiagonalLine()
        }
    }

    private fun createDiagonalLine(): List<Point> = mutableListOf<Point>().apply {
        if (x1 < x2 && y1 < y2) (x1..x2).forEachIndexed { index, x -> add(Point(x, y1 + index)) }
        if (x1 > x2 && y1 < y2) (x1 downTo x2).forEachIndexed { index, x -> add(Point(x, y1 + index)) }
        if (x1 < x2 && y1 > y2) (x1..x2).forEachIndexed { index, x -> add(Point(x, y1 - index)) }
        if (x1 > x2 && y1 > y2) (x1 downTo x2).forEachIndexed { index, x -> add(Point(x, y1 - index)) }
    }

    private fun createHorizontalLine(): List<Point> = mutableListOf<Point>().apply {
        if (x1 < x2) for (x in x1..x2) add(Point(x, y1))
        else for (x in x2..x1) add(Point(x, y1))
    }

    private fun createVerticalLine(): List<Point> = mutableListOf<Point>().apply {
        if (y1 < y2) for (y in y1..y2) add(Point(x1, y))
        else for (y in y2..y1) add(Point(x1, y))
    }
}

// e.g. 660,218 -> 660,339
private val regex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()

val convertToLine: (String) -> Line = { string ->
    val (x1, y1, x2, y2) = regex.find(string)?.destructured!!
    Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
}