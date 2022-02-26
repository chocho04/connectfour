fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    for (element in strings) {
        if (element == str) strings[strings.indexOf(element)] = "Banana"
    }
    return strings
}