package games.gameOfFifteen

import kotlin.math.pow

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val consideredValues = mutableSetOf<Int>()
    val sortedPermutation = permutation.sorted()
    var numberOfValuesPermutations = 0
    permutation.forEachIndexed { _, value ->
        run {
            numberOfValuesPermutations +=
                    (sortedPermutation.subList(0, sortedPermutation.indexOf(value)) - consideredValues).size
            consideredValues.add(value)
        }
    }
    return (-1).toDouble().pow(numberOfValuesPermutations.toDouble()).toInt() == 1
}