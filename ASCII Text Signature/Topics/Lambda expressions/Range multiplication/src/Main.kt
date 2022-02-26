    val lambda: (Long, Long) -> Long = {
        a: Long, b: Long ->
        var count = 1.toLong()
        for (i in a..b) { count *= i }
        count
    }
