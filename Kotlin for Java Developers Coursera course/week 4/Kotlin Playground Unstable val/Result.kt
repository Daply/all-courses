// Implement the property 'foo' so that it produced a different value on each access. 
// Note that you can modify the code outside the 'foo' getter (e.g. add additional imports or properties).

var value = 0
val foo: Int
    get() = value++

fun main(args: Array<String>) {
    // The values should be different:
    println(foo)
    println(foo)
    println(foo)
}