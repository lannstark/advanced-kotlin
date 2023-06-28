package dsl

import java.time.LocalDate

class OperatorOverloading {
}

data class Point(
  val x: Int,
  val y: Int,
) {
  operator fun unaryMinus(): Point {
    return Point(-x, -y)
  }

  operator fun unaryPlus(): Int {
    return x
  }

  operator fun inc(): Point {
    return Point(x + 1, y + 1)
  }
}

fun main() {
  var point = Point(20, -10)
  println(-point)
  println(++point)

  val list = listOf("A", "B", "C")
  list[2]
  val map = mutableMapOf(1 to "A")
  map[2] = "B"

  // 2023-01-04
  LocalDate.of(2023, 1, 1).plusDays(3)

  // 2023-01-04
  // 2023-12-29 <- 조금 문제가 있다~~
  LocalDate.of(2023, 1, 1) + Days(3)

  LocalDate.of(2023, 1, 1) + 3.d
}

data class Days(val day: Long)

val Int.d: Days
  get() = Days(this.toLong())

operator fun LocalDate.plus(days: Days): LocalDate {
  return this.plusDays(days.day)
}



