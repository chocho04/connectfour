class Cat {
    val name: String by lazy {
        println("I prefer to ignore it")
        new()
    }

    fun new(): String {
        println("Input the cat name")
        return readln()
    }
//    }
//
//}/**/