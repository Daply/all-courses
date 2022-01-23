
// Change the 'sum' function so that it was declared as an extension to List<Int>.

fun List<Int>.sumExt(): Int {
    var result = 0
    for (i in this) {
        result += i
    }
    return result
}

fun main(args: Array<String>) {
    val sum = listOf(1, 2, 3).sumExt()
    println(sum)    // 6
}