fun main() {
    fun Sequence<Int>.countIncreased() = this
        .windowed(2)
        .map { (previous, next) -> if (next > previous) "inc" else "dec" }
        .count { it == "inc" }


    fun part1(input: List<String>): Int {
        return input.asSequence()
            .map(String::toInt)
            .countIncreased()
    }

    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map(String::toInt)
            .windowed(3)
            .map(List<Int>::sum)
            .countIncreased()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
