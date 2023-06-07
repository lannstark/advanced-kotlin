package delegate

fun main() {
  val fruits = listOf(
    MyFruit("사과", 1000L),
    MyFruit("바나나", 3000L),
  )

  // 2,000,000 -> 모두 필터링!
  // [과일, 과일 과일, .. ]
  val avg = fruits.asSequence()
    .filter { it.name == "사과" }
    .map { it.price }
    .take(10_000)
    .average()
}

data class MyFruit(
  val name: String,
  val price: Long, // 1,000원부터 20,000원 사이
)
