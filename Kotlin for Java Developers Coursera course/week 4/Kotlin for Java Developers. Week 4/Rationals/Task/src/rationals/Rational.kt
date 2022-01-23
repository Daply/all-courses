package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

class Rational(var numerator: BigInteger, var denominator: BigInteger) {
    init {
        if (denominator == BigInteger.ZERO) throw IllegalArgumentException()
    }

    override operator fun equals(other: Any?): Boolean {
        if (other !is Rational) throw IllegalArgumentException()
        this.normalize()
        other.normalize()
        return this.numerator == other.numerator &&
                this.denominator == other.denominator
    }

    operator fun compareTo(other: Any?): Int {
        if (other !is Rational) throw IllegalArgumentException()
        val difference = this - other
        if (difference == Rational(BigInteger.ZERO, BigInteger.ONE)) return 0
        if (difference.isLessThanZero()) return -1
        return 1
    }

    override fun toString(): String {
        this.normalize()
        if (denominator == BigInteger.ONE) return numerator.toString()
        return "$numerator/$denominator";
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }
}

class RationalRange(private val start: Rational, private val endInclusive: Rational) {
    operator fun contains(value: Rational): Boolean = start <= value && value <= endInclusive
}

private fun Rational.normalize(): Rational {
    val greatestCommonDivisor = numerator.gcd(denominator)
    numerator /= greatestCommonDivisor
    denominator /= greatestCommonDivisor
    if (denominator.signum() != 1) {
        denominator = denominator.negate()
        numerator = numerator.negate()
    }
    return this
}

private fun Rational.isLessThanZero(): Boolean {
    return (numerator.signum() != 1 && denominator.signum() == 1) ||
            (numerator.signum() == 1 && denominator.signum() != 1)
}

operator fun Rational.plus(other: Rational): Rational {
    val resultDenominator = denominator * other.denominator
    val resultNumerator = numerator * other.denominator + other.numerator * denominator
    return Rational(resultNumerator, resultDenominator).normalize()
}

operator fun Rational.minus(other: Rational): Rational {
    val resultDenominator = denominator * other.denominator
    val resultNumerator = numerator * other.denominator - other.numerator * denominator
    return Rational(resultNumerator, resultDenominator).normalize()
}

operator fun Rational.times(other: Rational): Rational {
    val resultDenominator = denominator * other.denominator
    val resultNumerator = numerator * other.numerator
    return Rational(resultNumerator, resultDenominator).normalize()
}

operator fun Rational.div(other: Rational): Rational {
    val resultDenominator = denominator * other.numerator
    val resultNumerator = numerator * other.denominator
    return Rational(resultNumerator, resultDenominator).normalize()
}

operator fun Rational.unaryMinus(): Rational {
    return Rational(
            numerator.unaryMinus(),
            denominator
    ).normalize()
}

operator fun Rational.rangeTo(other: Rational): RationalRange {
    return RationalRange(this, other)
}

infix fun Int.divBy(other: Int): Rational =
        Rational(
                BigInteger.valueOf(this.toLong()),
                BigInteger.valueOf(other.toLong())
        )

infix fun Long.divBy(other: Long): Rational =
        Rational(
                BigInteger.valueOf(this),
                BigInteger.valueOf(other)
        )

infix fun BigInteger.divBy(other: BigInteger): Rational =
        Rational(this, other)

fun String.toRational(): Rational {
    if (!contains("/")) return Rational(this.toBigInteger(), BigInteger.ONE).normalize()
    val numerator = this.split("/")[0].toBigInteger()
    val denominator = this.split("/")[1].toBigInteger()
    return Rational(numerator, denominator).normalize()
}

fun main() {
    println("1/2".toRational().minus("2/3".toRational()))
    println("1/2".toRational().minus("2/3".toRational()).isLessThanZero())
    println("-1042438361047144366998/59812037109262381713".toRational()
            .minus("-1076615241954175969826/61773005685895342531".toRational()))
    println("-1042438361047144366998/59812037109262381713".toRational()
            .minus("-1076615241954175969826/61773005685895342531".toRational()).isLessThanZero())

    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}
