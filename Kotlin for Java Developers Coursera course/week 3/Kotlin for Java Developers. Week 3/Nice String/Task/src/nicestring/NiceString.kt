package nicestring

val vowels = listOf('a', 'e', 'i', 'o', 'u')

fun String.isNice(): Boolean {
    val unitedCharsByTwo =
            this.zipWithNext { a, b -> a.toString() + b.toString() }
    val containsSubstrings =
            !unitedCharsByTwo.any { listOf("bu", "ba", "be").contains(it) }
    val containsAtLeastThreeVowels = this.count { vowels.contains(it) } >= 3
    val containsDoubleLetter = unitedCharsByTwo.any { it.first() == it.last() }

    val conditions = listOf(containsSubstrings, containsAtLeastThreeVowels,
                containsDoubleLetter)
    return conditions.filter { it }.size >= 2
}