package function

class Lec15 {
}

fun main() {
  iterate(listOf(1, 2, 3, 4, 5)) { num ->
    if (num == 3) {
      // return // crossinline을 쓰면 사용을 할 수 없다.
    }
    println(num)
  }
}

inline fun iterate(numbers: List<Int>, crossinline exec: (Int) -> Unit) {
  for (num in numbers) {
    exec(num)
  }
}

inline fun repeat(
  times: Int,
  noinline exec: () -> Unit, // noinline 지시어를 붙였다!
) {
  for (i in 1..times) {
    exec()
  }
}

