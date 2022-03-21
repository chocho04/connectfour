fun main() {    
    val numbers = readLine()!!.split(' ').map { it.toInt() }.toIntArray()
    // Do not touch lines above
    val last = numbers.last()
    val first = numbers.first()
    numbers[0] = last
    numbers[numbers.size - 1] = first

    // Do not touch lines below
    println(numbers.joinToString(separator = " "))
}