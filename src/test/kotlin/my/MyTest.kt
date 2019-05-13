package my

fun main() {
    testQuotes()
}

private fun testQuotes() {
    val string = """jdsfk
        |asdfasdf
        |asdfasdf
        |asdf
        |asdf
        |asdf
        |
        |asdfasdf
    """
    print(string)
}

private fun testWhen() {
    val test: String
    val boolean: Boolean = true
    when (boolean) {
        true -> test = ""
        false -> test = "sdaf"
    }
    println(test)
}

class MyTest {

}

