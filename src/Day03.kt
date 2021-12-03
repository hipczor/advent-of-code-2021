fun main() {
    fun part1(input: List<String>): Int {
        val bits = input.map(intArray)
        var dominant = ""
        for (position in bits.first().indices) {
            dominant += bits.mostCommonBit(position)
        }
        var inverted = ""
        for (position in bits.first().indices) {
            inverted += bits.lessCommonBit(position)
        }
        return dominant.toInt(2) * inverted.toInt(2)
    }

    fun filterByCriteria(input: List<String>, criteria: (list: List<List<Int>>, position: Int) -> Int): Int? {
        var bits = input.map(intArray)
        for (position in bits.first().indices) {
            val bit = criteria(bits, position)
            bits = bits.filter { it[position] == bit }
            if (bits.size == 1) break
        }
        println(bits.first().toInt())
        return bits.firstOrNull()?.toInt()
    }

    fun part2(input: List<String>): Int {
        val oxygen = filterByCriteria(input) { list, position -> list.mostCommonBit(position) }!!
        val scrubber = filterByCriteria(input) { list, position -> list.lessCommonBit(position) }!!
        return oxygen * scrubber
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("Day03")
    println(part1(input))

    val testInput2 = readInput("Day03_test")
    check(part2(testInput2) == 230)
    println(part2(input))
}

private val intArray = { input: String ->
    input.toCharArray().map(Char::digitToInt)
}

fun List<List<Int>>.commonBit(position: Int) =
    map { it[position] }
        .groupingBy { it }
        .eachCount()

fun List<Int>.toInt() = joinToString(separator = "").toInt(radix = 2)

fun List<List<Int>>.mostCommonBit(position: Int): Int {
    val commonBits = commonBit(position)
    return if (commonBits.values.distinct().size == 1) {
        1
    } else {
        commonBits.maxByOrNull { it.value }?.key ?: throw IllegalArgumentException()
    }
}

fun List<List<Int>>.lessCommonBit(position: Int): Int {
    val commonBits = commonBit(position)
    return if (commonBits.values.distinct().size == 1) {
        0
    } else {
        commonBits.minByOrNull { it.value }?.key ?: throw IllegalArgumentException()
    }
}
