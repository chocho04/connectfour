import java.io.File

fun main() {
    val list = File("C:/Users/choch/Downloads/words_with_numbers.txt").readLines().toList()
    val regex = Regex("\\d")
    var counter = 0
    for (i in list) {
        if (i.contains(regex)) counter++
    }
    print(counter)
}
