fun bill(priceList: Map<String, Int>, shoppingList: MutableList<String>) =
    priceList.entries.filter { it.key in shoppingList }.sumOf { it.value }
