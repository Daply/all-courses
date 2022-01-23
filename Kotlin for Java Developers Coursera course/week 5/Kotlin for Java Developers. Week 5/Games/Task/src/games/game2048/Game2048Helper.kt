package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b     aa ab    a b b b a a -> ab bb bb ba aa -> a bb b aa
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    val listOfElements = mutableListOf<T>()
    val elementsAddedToPair = mutableSetOf<Int>()
    val listWithoutNulls = this.filterNotNull()
    listWithoutNulls.forEachIndexed { index, element ->
        run {
            if (index < listWithoutNulls.size - 1 && !elementsAddedToPair.contains(index) &&
                    listWithoutNulls[index] == listWithoutNulls[index + 1]) {
                elementsAddedToPair.addAll(listOf(index, index + 1))
                listOfElements.add(merge(element))
            } else if (!elementsAddedToPair.contains(index)) listOfElements.add(element)
        }
    }
    return listOfElements
}
