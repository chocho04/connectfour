package machine

enum class Machine(var water: Int, var milk: Int, var beans: Int, var money: Int, var cups: Int) {
    ESPRESSO(250, 0, 16, 4, 1 ),
    LATTE(350, 75, 20, 7, 1 ),
    CAPPUCCINO(200, 100, 12, 6, 1 );

    companion object {
        var currentWater = 400
        var currentMilk = 540
        var currentBeans = 120
        var currentMoney = 550
        var currentCups = 9

        fun state () {
            print("""The coffee machine has:
            $currentWater water of water
            $currentMilk of milk
            $currentBeans of coffee beans
            $currentCups of disposable cups
            $$currentMoney of money""".trimIndent())
        }

        fun fill() {
            println("Write how many ml of water do you want to add:")
            currentWater += readln().toInt()
            println("Write how many ml of milk do you want to add:")
            currentMilk += readln().toInt()
            println("Write how many grams of coffee beans do you want to add:")
            currentBeans += readln().toInt()
            println("Write how many disposable cups of coffee do you want to add:")
            currentCups += readln().toInt()
        }

        fun take() {
            println("I gave you $$currentMoney")
            currentMoney = 0
        }

        fun buy() {
            println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
            val input = readln()
            var drink = ESPRESSO

            when (input) {
                "1" -> drink = ESPRESSO
                "2" -> drink = LATTE
                "3" -> drink = CAPPUCCINO
                "back" -> return
            }
            if (currentWater < drink.water) println("Sorry, not enough water!\n")
            else if (currentMilk < drink.milk) println("Sorry, not enough milk!\n")
            else if (currentBeans < drink.beans) println("Sorry, not enough coffee beans!\n")
            else if (currentCups < drink.cups) println("Sorry, not enough cups!\n")
            else  { println("I have enough resources, making you a coffee!\n")
                currentWater -= drink.water
                currentMilk -= drink.milk
                currentBeans -= drink.beans
                currentCups -= drink.cups
                currentMoney += drink.money }
        }
    }
}

fun main() {
    while (true) {
        println("Write action (buy, fill, take, remaining, exit):")
        when (readln()) {
            "buy" -> Machine.buy()
            "fill" -> Machine.fill()
            "take" -> Machine.take()
            "remaining" -> Machine.state()
            "exit" -> break
        }
    }
}
