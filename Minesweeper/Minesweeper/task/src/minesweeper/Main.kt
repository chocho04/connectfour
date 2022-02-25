package minesweeper
import kotlin.random.Random

fun input(): Int {
    println("How many mines do you want on the field?")
    return readln().toInt()
}
class Field(private val rows: Int, private val columns: Int, private val mines: Int ) {

    var field = MutableList(rows) { MutableList(columns) { "." } } // create a field of dots

    init { placeMines() } // initialize the class with the mines already placed

    private fun placeMines(){ // place mines on the field, function only used by init
        while (field.toString().count { it == 'X' } < mines) { // while mines less than desired number
            val rX = Random.nextInt(1, rows) // choose a random column within the field
            val rY = Random.nextInt(1, columns) // choose a random row within the field
            field[rX][rY] = "X" // place a mine
        }
    }
    fun printField() { // show the current Field
        for (row in 0 until rows) { // INPUT row index
            for(column in 0 until columns) { // INPUT column index
                print(field[row][column])
            }
            println()
        }
    }
}
fun main() {
    val newGame = Field(9,9, input())
    newGame.printField()
}


