fun solution(products: List<String>, product: String) {
    var output = ""
    for (i in products.indices) {
        if (products[i] == product) output += "$i "
    }
    println(output)
}