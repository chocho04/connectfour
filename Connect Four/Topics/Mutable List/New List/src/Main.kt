fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    val nums = numbers.toMutableList()
    nums.add(number)
    return nums
}