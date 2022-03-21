fun removing(currentMap: MutableMap<Int, String>, value: String): MutableMap<Int, String> {
    val newMap = currentMap.toMutableMap()
    for ((k, v) in currentMap) {
        if (v == value) {
            newMap.remove(k)
        }
    }
    return newMap
}