fun helpingTheRobot(purchases: Map<String, Int>, addition: Map<String, Int>) : MutableMap<String, Int> {
    var new =  purchases.toMutableMap()
    for ((k, v) in addition) new[k] = if (new.containsKey(k)) new[k]!! + v else v
    return new
}