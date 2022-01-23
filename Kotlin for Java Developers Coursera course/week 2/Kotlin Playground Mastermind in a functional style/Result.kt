// You can skip this task for now. 
// We'll return to it at the end of the next module 
// after discussing functions for working with collections in a functional style.

// Complete the following implementation of 'evaluateGuess()' function. 
// Then compare your solution with the solution written in a functional style.

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    val rightPositions = secret.zip(guess).count { (first, second) -> first == second }

        val commonLetters = "ABCDEF".sumBy { ch ->

            Math.min(secret.count { it == ch }, guess.count { it == ch })
        }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}

fun main(args: Array<String>) {
    val result = Evaluation(rightPosition = 1, wrongPosition = 1)
    evaluateGuess("BCDF", "ACEB") eq result
    evaluateGuess("AAAF", "ABCA") eq result
    evaluateGuess("ABCA", "AAAF") eq result
}