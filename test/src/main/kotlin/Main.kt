import java.io.File

fun main() {
    val basedir = File("basedir")

    var dir = File("")
    basedir.walk(FileWalkDirection.TOP_DOWN).forEach { if (it.isDirectory && it.listFiles().isEmpty()) println(it) }


}