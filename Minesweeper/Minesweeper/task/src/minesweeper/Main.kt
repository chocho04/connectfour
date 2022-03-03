package minesweeper
import kotlin.random.Random
import kotlin.system.exitProcess

val m = MineField(11, 11, getMineChoice())

fun getMineChoice(): Int {
    println("How many mines do you want on the field?")
    return readln().toInt()
} // message mine choice and input
fun getMarkChoice(): String {
    println("Set/unset mines marks or claim a cell as free:")
    return readln()
} // mark choice and input

class MineField(var rows: Int, var columns: Int, val mines: Int ) {
    var field = MutableList(rows) { MutableList(columns) { "0" } } // the field with all the info
    var mField = MutableList(rows) { MutableList(columns) { "." } } // the field visible to the user, were copying data from field to here
    var visited = MutableList(rows) { MutableList(columns) { "0" } } // the field of visited positions for the BFS algorithm
}
class Control {
    companion object {
        private var starCount = 0 // variable for win condition
        private var first = true // check if the mines have been placed, =false after placing
        private fun placeMines(x : Int, y : Int, field: MutableList<MutableList<String>> = m.field,
                               rows: Int = m.rows, columns: Int = m.columns, mines: Int = m.mines) {
            while (field.toString().count { it == 'X' } < mines) { //random generating of mines
                val rX = Random.nextInt(1, rows - 1)
                val rY = Random.nextInt(1, columns - 1)
                field[rX][rY] = "X" // and deleting everything around our choice to ensure there are no mines there
                field[x][y - 1] = "0"; field[x][y + 1] = "0";     field[x + 1][y - 1] = "0"
                field[x + 1][y] = "0"; field[x + 1][y + 1] = "0"; field[x - 1][y - 1] = "0"
                field[x - 1][y] = "0"; field[x - 1][y + 1] = "0"; field[x][y] = "0"
            }
            for (r in 1 until rows - 1) {
                for (c in 1 until columns - 1) { // if we find X increment all surrounding positions with 1
                    if (field[r][c] == "X") { // turn them to int, increment then turn back into string
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
            for (r in 1 until rows - 1) { // replace the 0 with the free sign "/"
                for (c in 1 until columns - 1) {
                    if (field[r][c] == "0") field[r][c] = "/"
                }
            }
        }
        fun printState() {
            println(" │123456789│\n—│—————————│") // right now the field can be made bigger, but we have to change
            for (row in 1 until m.rows - 1) { // the numbers and lines to be dynamic
                print("$row│")
                for (col in 1 until m.columns - 1) {
                    print(m.mField[row][col])
                }
                print("│\n")
            }
            println("—│—————————│") // it would be:  print("—│"); repeat(columns) { print("—") }; print("│\n")
        }
        fun getMark(xy: String = getMarkChoice()) { // get our coordinates and free/mine choice
            val x = xy[2].toString().toInt()
            val y = xy[0].toString().toInt()
            val fm = xy.split(" ").last()
            placeMark(x,y,fm) // call the recursive function
        }
        private fun placeMark(x: Int, y: Int, choice: String) {
            val regex = Regex("\\d") // any digit
            when (choice) {
                "free" -> {
                    if (first) { placeMines(x, y); first = false} // the first "free" input place the mines
                    if (m.field[x][y] == "/") { // if the current coordinates are empty
                        m.mField[x][y] = m.field[x][y] // transfer them to the field visible to the user
                        if (x+1 < m.rows && y+1 < m.columns && x-1 >= 0 && y-1 >= 0 && m.visited[x][y] == "0") {
                            m.visited[x][y] = "1" // if coordinates are in bounds & not visited, mark visited
                            placeMark(x + 1, y, "free") // and recurse in all 8 directions
                            placeMark(x, y+1, "free")
                            placeMark(x + 1, y+1, "free")
                            placeMark(x - 1, y, "free")
                            placeMark(x - 1, y+1, "free")
                            placeMark(x - 1, y-1, "free")
                            placeMark(x + 1, y-1, "free")
                            placeMark(x, y-1, "free")
                        }
                    } else if (m.field[x][y].matches(regex)) { // if the coordinates reach a number
                        m.mField[x][y] = m.field[x][y] // transfer to visible field mField without recursion
                    } else if (m.field[x][y] == "X") { // if you step on a mine
                        for (row in 1 until m.rows) { // show all the mines
                            for(column in 1 until m.columns) {
                                if (m.field[row][column] == "X") { m.mField[row][column] = "X" }
                            }
                        }
                        printState() // print the field and END
                        println("You stepped on a mine and failed!")
                        exitProcess(0)
                    }
                }
                "mine" -> { // placing a mine
                    when (m.mField[x][y]) {
                        "." -> { m.mField[x][y] = "*"; starCount++ } // track how many stars you've placed
                        "*" -> { m.mField[x][y] = "."; starCount-- }
                    }
                }
                else -> println("Invalid input!")
            }
        }
        fun checkWin() { // check for winning conditions
            var winCount = 0 // track correctly placed mines
            var empty = 0 // track empty coordinates left

            for (r in 1 until m.rows - 1) {
                for (c in 1 until m.columns - 1) {
                    if (m.mField[r][c] == ".") empty++ // count the empty coordinates left
                    if (m.field[r][c] == "X" && m.mField[r][c] == "*") { // count correct guesses
                        winCount++
                    }
                }
            }
            if ((winCount == starCount && winCount == m.mines) || // if correct guesses = stars places & = all mines
                (m.mines == empty && starCount == 0)) { // or if there are no stars placed & number of mines = empty coordinates
                printState()
                println("Congratulations! You found all the mines!") // we win!
                exitProcess(0)
            }
        }
    }
}
fun main() {
    while (true) { // this can be placed in a separate gameplay class
        Control.printState()
        Control.getMark()
        Control.checkWin()
    }
}