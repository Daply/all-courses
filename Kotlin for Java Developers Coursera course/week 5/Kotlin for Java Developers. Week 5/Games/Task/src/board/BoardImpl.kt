package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

open class SquareBoardImpl(
        private val size: Int
): SquareBoard {
    private val rows: List<List<Cell>>
    override val width: Int
        get() {
            return size
        }

    init {
        val numberings = 1..size
        this.rows = numberings.map { i ->
            numberings.map { j -> Cell(i, j) } }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (this.rows.isEmpty() || this.width < j || this.width < i) return null
        return this.rows[i - 1][j - 1]
    }

    override fun getCell(i: Int, j: Int): Cell = this.rows[i - 1][j - 1]

    override fun getAllCells(): Collection<Cell> = this.rows.flatten()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        if (this.rows.isEmpty()) return listOf()
        return if (jRange.first <= jRange.last)
            this.rows[i - 1].filterIndexed{ index, _ ->
                index >= jRange.first - 1 && index <= jRange.last - 1 }
        else this.rows[i - 1].filterIndexed{ index, _ ->
            index >= jRange.last - 1 && index <= jRange.first - 1 }.reversed()
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        if (this.rows.isEmpty()) return listOf()
        return if (iRange.first <= iRange.last)
            this.rows.filterIndexed { index, _ ->
                index >= iRange.first - 1 && index <= iRange.last - 1 }
                    .map { it[j - 1] }
        else this.rows.filterIndexed { index, _ ->
            index >= iRange.last - 1 && index <= iRange.first - 1 }
                .map { it[j - 1] }.reversed()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction) {
            UP -> if (this.i <= 1) null else rows[this.i - 2][this.j - 1]
            LEFT -> if (this.j <= 1) null else rows[this.i - 1][this.j - 2]
            DOWN -> if (this.i >= width) null else rows[this.i][this.j - 1]
            RIGHT -> if (this.j >= width) null else rows[this.i - 1][this.j]
        }
    }
}

class GameBoardImpl<T>(override val width: Int) : GameBoard<T>, SquareBoardImpl(width) {
    private val values = mutableMapOf<Cell, T?>()

    init {
        super.getAllCells().forEach{ cell -> values[cell] = null }
    }

    override operator fun get(cell: Cell): T? = values[cell]
    override operator fun set(cell: Cell, value: T?) = run { values[cell] = value }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
            values.filter { entry -> predicate.invoke(entry.value) }.keys
    override fun find(predicate: (T?) -> Boolean): Cell? =
            values.entries.find { entry -> predicate.invoke(entry.value) }?.key
    override fun any(predicate: (T?) -> Boolean): Boolean =
            values.entries.any { entry -> predicate.invoke(entry.value) }
    override fun all(predicate: (T?) -> Boolean): Boolean =
            values.entries.all { entry -> predicate.invoke(entry.value) }

}