fun solution(numbers: List<Int>) {
    var output = ""
    for (i in numbers) {
        if (i % 2 == 0) output += "$i "
    }
    println(output)
}