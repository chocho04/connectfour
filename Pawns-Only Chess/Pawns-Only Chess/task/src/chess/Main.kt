package chess
import kotlin.system.exitProcess

var board = MutableList(64) { if (it in 8..15) 0 else if (it in 48..55) 1 else 2 }
var players = listOf<String>()
var p = 1 // player
var lastMove = 0

fun print() {
    var col = 56 ; var row = 8 ; val line = "  +---+---+---+---+---+---+---+---+\n"
    repeat(8) { print(line); print("$row "); row--
        for (i in col  .. col+7) {
            if (board[i] == 0) print("| W ") else if (board[i] == 1) print("| B ") else print("|   ") }
        col-=8; print("|\n") }
    print(line); print("    a   b   c   d   e   f   g   h\n")
}
fun names() {
    println("First Player's name:"); val p1 = readln()
    println("Second Player's name:"); val p2 = readln()
    players = listOf(p1,p2)
}
fun play() {
    Valid.stalemate()
    println("${players[p]}'s turn:")
    val input = readln()
    val (from, to) = input.chunked(2).map{ (it[0].code + it[1].code*8 - 489) }

    if (input == "exit") { println("Bye!"); exitProcess(0) } // 1
    if (board[from] != p) { println("No ${if (p==0) "white" else "black"} pawn at ${input.slice(0..1)}")
        play() } // 2
    else if (Valid.front(from, to) || Valid.side(from, to) && board[to] == 1 - p
        || Valid.start(from, to) || Valid.enpas(from, to)) {
        board[from] = 2; board[to] = p; lastMove = to
        if (to in 0..7 || to in 56..63 || Valid.winByTake()) { print() // win
            println("${if (p==0) "\nWhite" else "Black"} Wins! \nBye! "); exitProcess(0) }
    } // 3
    else { println("Invalid Input"); play() }
}
class Valid {
    companion object {
        fun p(x:Int) = if (p == 0) x else -x // player
        fun front(from: Int, to: Int) = to == from + p(8) && board[to] == 2 // front move 1 tile
        fun side(from: Int, to: Int) = to == from + p(7) || to == from + p(9) // diagonal take
        fun start(from: Int, to: Int) = (from in if (p == 0) 8..15 else 48..55) && // start move 1 or 2 tiles
                (to == from + p(16) || front(from, to)) && board[to] == 2
        fun enpas(from: Int, to: Int): Boolean { // en passe take
            var bool = false
            if ((side(from, to) && board[to] == 2) && (to in if (p == 0) 40..47 else 16..23)
                && board[to + p(-8)] == 1 - p && lastMove == to + p(-8)) {
                board[lastMove] = 2
                bool = true
            }
            return bool
        }
        fun winByTake(): Boolean {
            var count = 0
            for (i in board.indices) { if (board[i] == 1 - p) count++}
            return count == 0
        }
        fun stalemate() {
            var moves = 0
            for (fr in 8..63) {
                for (t in board.indices) {
                    if ( board[fr] == p && (front(fr,t) || start(fr,t) || (side(fr,t) && board[t] == 1 - p))) {
                        moves++ }
                }
            }
            if (moves == 0) { println("Stalemate!\nBye!"); exitProcess(0) }
        }
    }
}
fun main() {
    println("Pawns-Only Chess"); names(); print()
    while (true) { p = 1 - p; play(); print() }
}