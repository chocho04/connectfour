package connectfour

class Game {
    private var player1 = ""
    var p1points = 0
    private var player2 = ""
    var p2points = 0
    private var rows = 0
    private var columns = 0
    var games = 0 // chosen amount of games -> play()
    var gamesToPlay = 0 // make a counter for number of games to be played -> play()
    var turn = 0 // counter to flip turns on even/odd -> play()

    // create a game board as a list of lists
    var boardList = MutableList(rows) { MutableList(columns + 1) { mutableListOf("║", " ") } }

    fun players() {
        println("Connect Four")
        println("First player's name:")
        player1 = readln()
        println("Second player's name:")
        player2 = readln()
    }
    fun boardSetup() {
        while (true) {
            println("Set the board dimensions (Rows x Columns)")
            println("Press Enter for default (6 x 7)")
            // check - 1 up to 3 digits on either side of the x
            val regex = Regex("\\d?\\d?\\dx\\d\\d?\\d?")
            // get the input, strip spaces and tab
            val input = readln().replace(" ", "").replace("\t", "").lowercase()
            if (input.isBlank()) {
                rows = 6
                columns = 7
                break
            }
            if (!input.matches(regex)) println("Invalid input")
            else {
                rows = input.first().toString().toInt() // get the left digit
                columns = input.last().toString().toInt() //get the right digit
                if (rows !in 5..9) println("Board rows should be from 5 to 9")
                else if (columns !in 5..9) println("Board columns should be from 5 to 9")
                else break
            }
        }

    }
    fun play() {
        boardList = MutableList(rows) { MutableList(columns + 1) { mutableListOf("║", " ") } } // set the board size

        var currentPlayer: String // used to print who won the game
        var token: String
        var run = true // used to break out of the while loop when the game ends

        if (games != 1) println("Game #${games + 1 - gamesToPlay}")
        printBoard()
        while (run && gamesToPlay > 0) { // check the counter
            token = if (turn % 2 == 0) { // alternate turns with incrementing even/odd number
                currentPlayer = player1
                println("$player1's turn:")
                "o" // set player1 token
            } else {
                currentPlayer = player2
                println("$player2's turn:")
                "*" // set player 2 token
            }
            val input = readln() // get user input
            val regex = Regex("\\d?\\d?\\d") // up to triple digit

            when {
                input == "end" -> {
                    println("Game over!")
                    break
                }
                !input.matches(regex) -> {
                    println("Incorrect column number")
                    continue
                } // check if input is a single digit
                input.toInt() !in 1..columns -> {
                    println("the column number is out of range (1 - $columns)")
                    continue
                } // check if user input is in range of the current columns
                boardList[0][input.toInt() - 1][1] != " " -> {
                    println("Column $input is full")
                    continue
                } // check if the top most field of the current column is taken
            }
            for (i in boardList.size - 1 downTo 0) {  // go through column from bottom to top
                if (boardList[i][input.toInt() - 1][1] == " ") { // if current field is empty
                    boardList[i][input.toInt() - 1][1] = token  // set the current player token
                    printBoard()
                    when (win(token, boardList)) { //pass the board and the current token into win function
                        "win" -> if (games == 1) { // if a single game is played thats all
                            println("Player $currentPlayer won\nGame Over!")
                            run = false } // terminate loop and current function
                            else { //if the win is a part of multiple games
                                println("Player $currentPlayer won\nScore")
                                if (currentPlayer == player1) p1points += 2 else p2points += 2 //attribute points
                                println("$player1: $p1points $player2: $p2points")
                                gamesToPlay-- // decrement counter for 1 less game to be played
                                if (gamesToPlay == 0) println("Game over!") else { turn++
                                    play() }
                            }
                        "draw" -> if (games == 1) { println("It is a draw\nGame Over!")
                            run = false }
                            else {
                                println("It is a draw\nScore")
                                p1points++ // attribute each player 1 point
                                p2points++
                                println("$player1: $p1points $player2: $p2points")
                                gamesToPlay--
                                if (gamesToPlay == 0) println("Game over!") else { turn++
                                    play() }
                            }
                    }
                    turn++ // change whose turn it is
                    break
                }
            }
        }
    }
    fun chooseGames() {
        while (true) {
            println("Do you want to play single or multiple games?")
            println("For a single game, input 1 or press Enter")
            println("Input a number of games:")
            val inputGames = readln()
            val gamesRegex = Regex("^\\+?(0|[1-9]\\d*)\$") // positive int
            when {
                inputGames == "" || inputGames == "1" -> { // single game choice
                    games = 1
                    gamesToPlay = games
                    println("$player1 VS $player2")
                    println("$rows X $columns board")
                    println("Single game")
                    break
                }
                inputGames.matches(gamesRegex) && inputGames.toInt() > 1 -> { // multiple games choice
                    games = inputGames.toInt()
                    gamesToPlay = games // set the counter for the play() - function: Game#..
                    println("$player1 VS $player2")
                    println("$rows X $columns board")
                    println("Total $games games")
                    break
                }
                else -> println("Invalid input")
            }
        }
    }
    private fun printBoard() {
        var i = 1
        repeat(columns) { print(" ${i++}") } // print the column numbers
        println()
        print( boardList.joinToString() // turn the board list into a string
                .replace(", ", "") // strip the commas
                .replace("]]", "\n") // format each row to new line
                .replace("][", "")
                .replace("[[", "")) // strip front brackets
        print("╚═")
        repeat(columns - 1) { print("╩═") } //print bottom row
        print("╝\n")
    }
    private fun win(token: String, list: MutableList<MutableList<MutableList<String>>>): String {
        val check = list.joinToString() // turn the board 2d list into a string and strip everything but the spaces
            .replace(",", "")
            .replace("[", "")
            .replace("] ║", "")
            .replace("]", "")
            .replace(token, "X") // replace * with another character otherwise it throws an error

        val cp = columns * 2 + 1 // space between X for a column win
        val dpr = columns * 2 - 1 // space between X for a right diagonal win
        val dpl = columns * 2 + 3 // space between X for a left diagonal win

        val regexC = "X(.{$cp}X){3}".toRegex() //column pattern space between (dependant on column size) X times 3
        val regexD1 = "X(.{$dpl}X){3}".toRegex() // win by left diagonal
        val regexD2 = "X(.{$dpr}X){3}".toRegex() // win by right diagonal
        val regexR = "X X X X".toRegex() // win by row
        val regexDraw = "( [^ ]){$columns}".toRegex() // draw, checks if every second character in a column is not a space

        when {
            (check.contains(regexC) || check.contains(regexD1) || check.contains(regexD2) || check.contains(regexR)) -> return "win"
            (check.slice(0..cp).contains(regexDraw)) -> return "draw" // checks only top column
        }
        return ""
    }
}
fun main() {
    val newGame = Game()
    newGame.players()
    newGame.boardSetup()
    newGame.chooseGames()
    newGame.play()
}
