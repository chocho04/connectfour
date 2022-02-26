class Employee(val id: Int, val name: String, val lastName: String, val telNum: String, val email: String) {

    fun printData() {
        println("Employee $id")
        println("full name: $name $lastName")
        println("tel. num: $telNum")
        println("email: $email")
    }
}
fun main() {
    val count = readLine()!!.toInt()

    createEmployees(employeeCreator = ::Employee, count)
}
fun createEmployees(employeeCreator: (Int, String, String, String, String) -> Employee, count: Int) {
    for (i in 1..count) {
        val (name, lastName, telNum, email) = readLine()!!.split(' ').toList()

        val newEmployee = Employee(i, name, lastName, telNum, email )
        newEmployee.printData()

    }
}