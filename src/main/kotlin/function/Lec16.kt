package function

fun add(a: Int, b: Int) = a + b

fun main() {

  val add1 = { a: Int, b: Int -> a + b }

  val add2 = fun (a: Int, b: Int) = a + b

  ::add

  //KStringFilter { it.startsWith("A") }
}

fun consumeFilter(filter: StringFilter) { }

fun <T> consumeFilter(filter: Filter<T>) {}

fun interface KStringFilter {
  fun predicate(str: String): Boolean
}