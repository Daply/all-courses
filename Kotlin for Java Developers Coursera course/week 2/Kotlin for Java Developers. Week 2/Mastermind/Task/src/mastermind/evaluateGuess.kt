package mastermind

import kotlin.math.min

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

private fun compareStringsLettersAndPositions(secret: String, guess: String): Pair<Int, Int> {
    var rightPosition = 0
    var wrongPosition  = 0
    guess.forEachIndexed{ index, char -> if (secret[index] == char) rightPosition++ }
    val letterFrequenciesSecret = secret.groupingBy { it }.eachCount()
    val letterFrequenciesGuess = guess.groupingBy { it }.eachCount()
    letterFrequenciesGuess.forEach{
        if (letterFrequenciesSecret.containsKey(it.key) &&
                letterFrequenciesSecret.get(it.key) != null) {
            wrongPosition += min(it.value, letterFrequenciesSecret.get(it.key)!!)
        }
    }

    return Pair(rightPosition, wrongPosition - rightPosition)
}

fun evaluateGuess(secret: String, guess: String): Evaluation {
    return compareStringsLettersAndPositions(secret, guess)
            .let { Evaluation(it.first, it.second) }
}

fun main() {
    println(evaluateGuess("ABCD", "ABCD") == Evaluation(4, 0)) // 4 0
    println(evaluateGuess("ABCD", "CDBA") == Evaluation(0, 4)) // 0 4
    println(evaluateGuess("ABCD", "ABDC") == Evaluation(2, 2)) // 2 2
    println(evaluateGuess("AABC", "ADFE") == Evaluation(1, 0)) // 1 0
    println(evaluateGuess("AABC", "ADFA") == Evaluation(1, 1)) // 1 1
    println(evaluateGuess("AABC", "DFAA") == Evaluation(0, 2)) // 0 2
    println(evaluateGuess("AABC", "DEFA") == Evaluation(0, 1)) // 0 1
    println(evaluateGuess("ABCD", "EAAA") == Evaluation(0, 1)) // 0 1
    println(evaluateGuess("BDAD", "AAAE") == Evaluation(1, 0)) // 1 0
}
