fun main() {
    fun part1(input: List<String>): Int {
        val (numbers, boards) = readBingo(input)
        for (number in numbers) {
            boards.forEach { it.addNumber(number) }
            boards.find { it.bingo() }?.let {
                return it.score()
            }
        }

        throw Exception("No bingo :(")
    }

    fun part2(input: List<String>): Int {
        val (numbers, boards) = readBingo(input)
        val bingos = mutableSetOf<Board>()
        for (number in numbers) {
            boards.forEach {
                it.addNumber(number)
                if (it.bingo()) bingos.add(it)
            }
        }
        return bingos.last().score()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("Day04")
    println(part1(input))

    val testInput2 = readInput("Day04_test")
    check(part2(testInput2) == 1924)
    println(part2(input))
}


fun readBingo(input: List<String>): Pair<IntArray, List<Board>> {
    val numbers = input[0].split(",").map { it.toInt() }.toIntArray()
    val boards = input
        .drop(1)
        .filter { it.isNotBlank() }
        .windowed(size = 5, step = 5)
        .map { board ->
            board.map { row ->
                row.windowed(size = 3, step = 3, partialWindows = true)
                    .map { it.trim().toInt() }
            }
        }.map { Board(mutableListOf(), it) }
    return numbers to boards
}

data class Board(
    private val numbers: MutableList<Int>,
    private val board: List<List<Int>>
) {
    fun addNumber(number: Int) {
        if (!bingo()) numbers.add(number)
    }

    fun bingo(): Boolean = checkHorizontalBingo() || checkVerticalBingo()

    fun score() = board.flatten().filter { it !in numbers }.sum() * numbers.last()

    private fun checkHorizontalBingo() = board.any { row -> row.all { number -> number in numbers } }

    private fun checkVerticalBingo(): Boolean {
        for (i in board.first().indices) {
            val bingo = board.map { row -> row[i] }.all { number -> number in numbers }
            if (bingo) {
                return true
            }
        }
        return false
    }
}