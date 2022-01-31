package machine

fun main() {
    println("Write how many ml of water the coffee machine has:")
    var water = readln().toInt()
    println("Write how many ml of milk the coffee machine has:")
    var milk = readln().toInt()
    println("Write how many g of coffee beans the coffee machine has:")
    var beans = readln().toInt()
    println("Write how many cups of coffee you will need:")
    val input = readln().toInt()

    var cups = 0
    while (water >= 200 && milk >= 50 && beans >= 15) {
        water -= 200
        milk -= 50
        beans -= 15
        cups++
    }

    when {
        cups == input -> println("Yes, I can make that amount of coffee")
        cups < input -> println("No i can make only $cups cups of coffee")
        cups > input -> println("Yes, I can make that amount of coffee (and even ${cups - input} more than that)")
    }
}
