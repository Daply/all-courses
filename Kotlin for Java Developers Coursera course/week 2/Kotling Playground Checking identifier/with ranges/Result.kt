
// Implement the function that checks whether a string is a valid identifier. 
// A valid identifier is a non-empty string that starts with a letter or underscore 
// and consists of only letters, digits and underscores.

fun isValidIdentifier(s: String): Boolean {
    fun String.firstCharIn(listOfChars: ClosedRange<Char>) = this[0] in listOfChars
    fun String.allCharsIn(listOfChars: List<Char>) : Boolean {
        for (c in this) {
            if (c !in listOfChars) return false
        }
        return true
    }
    return (s.isNotEmpty()) && (s.firstCharIn('a'..'z') || s.firstCharIn('A'..'Z') ||
            s[0] == '_') && s.allCharsIn(('a'..'z') + ('A'..'Z') + ('0'..'9') + '_')
}

fun main(args: Array<String>) {
    println(isValidIdentifier("name"))   // true
    println(isValidIdentifier("_name"))  // true
    println(isValidIdentifier("_12"))    // true
    println(isValidIdentifier(""))       // false
    println(isValidIdentifier("012"))    // false
    println(isValidIdentifier("no$"))    // false
}