fun identity(num: Int): Int = num
fun half(num: Int): Int = num/2
fun zero(num: Int = 0) = 0

fun generate(functionName: String): (Int) -> Int {
    return when (functionName) {
        "identity" -> ::identity
        "half" -> ::half
        "zero" -> ::zero
        else -> ::zero
    }
}


