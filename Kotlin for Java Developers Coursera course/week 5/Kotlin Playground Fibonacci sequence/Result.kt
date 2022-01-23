// Implement the function that builds a sequence of Fibonacci numbers using 'sequence' function. Use 'yield'.

fun fibonacci(): Sequence<Int> = sequence {
    var numbersList = mutableListOf<Int>()
    numbersList.addAll(listOf(0, 1))
    var index = 2
    while (true) {
        numbersList.add(numbersList[index - 1] + numbersList[index - 2])
        yield(numbersList[index - 2])
        index++
    }
}

fun main(args: Array<String>) {
    fibonacci().take(4).toList().toString() eq
            "[0, 1, 1, 2]"

    fibonacci().take(10).toList().toString() eq
            "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34]"
}