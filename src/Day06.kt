fun main() {
    fun part1(input: List<String>): Int {
        val lanternFishes = input
            .first()
            .split(",")
            .map(String::toInt)
            .map(::LanternFish)
            .toMutableList()

        for (day in 0 until 80) {
            val newFishes = lanternFishes.mapNotNull(LanternFish::incrementTimer)
            lanternFishes.addAll(newFishes)
        }
        return lanternFishes.size
    }

    fun part2(input: List<String>): Long {
        val lanternFishes = input.first()
            .split(",")
            .map(String::toInt)
            .map(::LanternFish)
            .toMutableList()

        var sum: Long = 0

        for (fish in lanternFishes) {
            val list = mutableListOf(fish)
            for (day in 0 until 80) {
                val newFishes = list.mapNotNull(LanternFish::incrementTimer)
                list.addAll(newFishes)
            }
            println(list.map { it.timer }.joinToString(","))
            sum += list.size
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)

    val input = readInput("Day06")
    println(part1(input))

    val testInput2 = readInput("Day06_test")
    check(part2(testInput2) == 5934L)
    println(part2(input))
}

data class LanternFish(var timer: Int) {

    fun incrementTimer(): LanternFish? {
        return if (timer == 0) {
            timer = 6
            LanternFish(timer = 8)
        } else {
            timer -= 1
            null
        }
    }

}