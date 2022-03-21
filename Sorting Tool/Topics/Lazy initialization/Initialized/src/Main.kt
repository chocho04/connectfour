class B {
    lateinit var b: String

    fun checkB() : Boolean {
        return ::b.isInitialized
    }
}
