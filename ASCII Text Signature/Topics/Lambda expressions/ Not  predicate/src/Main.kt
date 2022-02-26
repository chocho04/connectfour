import java.io.File
fun main() {
    val fileName = "src/words_sequence.txt"
    val file = File(fileName)
    val lines = file.readText()
    print(lines)
}