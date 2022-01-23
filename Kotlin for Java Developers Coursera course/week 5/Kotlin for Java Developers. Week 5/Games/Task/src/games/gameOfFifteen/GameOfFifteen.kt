package games.gameOfFifteen

import board.Cell
import board.Direction
import board.Direction.*
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer): Game {
    private val board = createGameBoard<Int>(4)
    private var emptyCell: Cell? = null

    override fun initialize() {
        board.getAllCells().zip(initializer.initialPermutation)
                .forEach { board[it.first] = it.second }
        emptyCell = board.getAllCells().last()
    }

    private fun getNeighbourOrNull(cell: Cell, direction: Direction): Cell? {
        when(direction) {
            RIGHT -> emptyCell?.let { return board.getCellOrNull(cell.i, cell.j - 1) }
            LEFT -> emptyCell?.let { return board.getCellOrNull(cell.i, cell.j + 1) }
            UP -> emptyCell?.let { return board.getCellOrNull(cell.i + 1, cell.j) }
            DOWN -> emptyCell?.let { return board.getCellOrNull(cell.i - 1, cell.j) }
        }
        return null
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        return board.getAllCells().zip(1..15)
                .all { board[it.first] == it.second }
    }

    override fun processMove(direction: Direction) {
        emptyCell?.let {
            val neighbour: Cell? = getNeighbourOrNull(it, direction)
            if (neighbour != null) {
                board[it] = board[neighbour]
                board[neighbour] = null
                emptyCell = neighbour
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}
