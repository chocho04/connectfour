package minesweeper
import kotlin.random.Random

fun input(): Int {
    println("How many mines do you want on the field?")
    return readln().toInt()
}
open class setField(private var rows: Int, private var columns: Int, private val mines: Int ) {

    var field = MutableList(rows) { MutableList(columns) { "0" } }
    var markField = MutableList(rows) { MutableList(columns) { "." } }

    init { placeMines(); setField(); fieldToMarkField() }

    private fun placeMines(){
        while (field.toString().count { it == 'X' } < mines) {
            val rX = Random.nextInt(1, rows - 1)
            val rY = Random.nextInt(1, columns - 1)
            field[rX][rY] = "X"
        }
    }
    private fun setField() { // show the current Field
        for (r in 1 until rows - 1) {
            for (c in 1 until columns - 1) {
                if (field[r][c] == "X") {
                    if ( field[r][c-1] != "X") field[r][c-1] = (field[r][c-1].toInt() + 1).toString()
                    if ( field[r][c+1] != "X") field[r][c+1] = (field[r][c+1].toInt() + 1).toString()
                    if ( field[r+1][c-1] != "X") field[r+1][c-1] = (field[r+1][c-1].toInt() + 1).toString()
                    if ( field[r+1][c] != "X") { field[r+1][c] = (field[r+1][c].toInt() + 1).toString() }
                    if ( field[r+1][c+1] != "X") field[r+1][c+1] = (field[r+1][c+1].toInt() + 1).toString()
                    if ( field[r-1][c-1] != "X") field[r-1][c-1] = (field[r-1][c-1].toInt() + 1).toString()
                    if ( field[r-1][c] != "X") field[r-1][c] = (field[r-1][c].toInt() + 1).toString()
                    if ( field[r-1][c+1] != "X") field[r-1][c+1] = (field[r-1][c+1].toInt() + 1).toString()
                }
            }
        }
        for (r in 1 until rows - 1) {
            for (c in 1 until columns - 1) {
                if (field[r][c] == "0") field[r][c] = "."
            }
        }
    }
    private fun fieldToMarkField() {
        for (row in 0 until field.size) {
            for (col in 0 until field[row].size) {
                field[row][col] = markField[row][col]
            }
        }


}


    class Game() {

    }

    fun printState(field: MutableList<MutableList<String>>) {
        for (row in 1 until rows - 1) { // INPUT row index
            for (col in 1 until columns - 1) { // INPUT column index
                if (field[row][col] == "X") print(".")
                else print(field[row][col])
            }
            println()
        }
    }
    fun setMark(xy: String) {
        val x = xy.first().toString().toInt()
        val y = xy.last().toString().toInt()
        val regex = Regex("\\d")
        if (field[x][y].matches(regex)) {
            println(" There is a number here!")

        } else if  (field[x][y] == ".") { markField[x][y] = "*" }
        else if (markField[x][y] == "*") { markField[x][y] = "." }
        else { markField[x][y] = "*" }
    }
}

fun main() {
    val newGame = Field(11,11, input())

    newGame.printState(newGame.field)
    while (true) {
        println("Set/delete mines marks (x and y coordinates):")
        newGame.setMark(readln())
        newGame.printState(newGame.field)
        newGame.printState(newGame.markField)
    }
}