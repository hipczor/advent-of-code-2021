fun main() {

    fun part1(input: List<String>): Int {
        val commands = input.map { it.split(" ") }
            .groupBy(keySelector = { it.first() }, valueTransform = { (_, value) -> value.toInt() })
        val forward = commands["forward"]?.sum() ?: 0
        val up = commands["up"]?.sum() ?: 0
        val down = commands["down"]?.sum() ?: 0
        return forward * (down - up)
    }

    fun part2(input: List<String>): Int {
        val position = input.map { it.split(" ") }
            .fold(Position()) { currentPosition: Position, (command, value) ->
                when (command) {
                    "forward" -> currentPosition.updateHorizontalPosition(value.toInt())
                    "up" -> currentPosition.updateAim(-value.toInt())
                    "down" -> currentPosition.updateAim(value.toInt())
                    else -> throw IllegalArgumentException("Unknown command $command")
                }
            }
        println(position)
        return position.horizontal * position.depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("Day02")
    println(part1(input))

    val testInput2 = readInput("Day02_test")
    check(part2(testInput2) == 900)
    println(part2(input))
}

data class Position(val depth: Int = 0, val horizontal: Int = 0, val aim: Int = 0) {
    fun updateHorizontalPosition(value: Int): Position {
        val newDepth = if (aim == 0) depth else depth + (aim * value)
        return copy(depth = newDepth, horizontal = horizontal + value, aim = aim)
    }

    fun updateAim(value: Int) = copy(aim = aim + value)
}

